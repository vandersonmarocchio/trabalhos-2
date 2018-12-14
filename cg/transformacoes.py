import math
import numpy as np
from PIL import ImageTk


def clear(pilimage, canvas):
    pixels = pilimage.load()

    for i in range(pilimage.size[0]):
        for j in range(pilimage.size[1]):
            pixels[i, j] = 255

    canvas._image_tk = ImageTk.PhotoImage(pilimage)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def piltomatrix(pilimage):
    list_x = []
    list_y = []
    pixels = pilimage.load()

    for i in range(pilimage.size[0]):
        for j in range(pilimage.size[1]):
            if pixels[i, j] != 255:
                list_x.append(i)
                list_y.append(pilimage.size[1] - j)

    matrix = np.matrix([list_x, list_y, [1] * len(list_x)])
    return matrix


def escala(pilimage, canvas, scx, scy):
    def findoriginpx():
        pixels = pilimage.load()
        orgx = 0
        orgy = 0
        for j in range(pilimage.size[1]):
            for i in range(pilimage.size[0] - 1, 0, -1):
                if pixels[i, j] != 255:
                    orgy = j
        for i in range(pilimage.size[0] - 1, 0, -1):
            for j in range(pilimage.size[1]):
                if pixels[i, j] != 255:
                    orgx = i
        return (orgx, pilimage.size[1] - orgy)

    def trypaint(ptx, pty):
        try:
            pixels[ptx, pilimage.size[1] - pty] = 0
        except IndexError:
            pass

    matrix = piltomatrix(pilimage)
    fsp = findoriginpx()
    transformation = np.matrix([[scx, 0, fsp[0] - fsp[0] * scx],
                                [0, scy, fsp[1] - fsp[1] * scy],
                                [0, 0, 1]])
    results = transformation * matrix
    clear(pilimage, canvas)
    pixels = pilimage.load()

    for column in range(results.shape[1]):
        ptx = int(results[0, column])
        pty = int(results[1, column])
        trypaint(ptx, pty)
        for adjx in range(int(math.floor(scx)) + 1):
            for adjy in range(int(math.floor(scy)) + 1):
                trypaint(ptx - adjx, pty - adjy)

    canvas._image_tk = ImageTk.PhotoImage(pilimage)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def translacao(pilimage, canvas, mvx, mvy):
    matrix = piltomatrix(pilimage)
    transformation = np.matrix([[1, 0, mvx],
                                [0, 1, mvy],
                                [0, 0, 1]])
    results = transformation * matrix
    clear(pilimage, canvas)
    pixels = pilimage.load()

    for column in range(results.shape[1]):
        ptx = int(results[0, column])
        pty = int(results[1, column])
        try:
            pixels[ptx, pilimage.size[1] - pty] = 0
        except IndexError:
            pass

    canvas._image_tk = ImageTk.PhotoImage(pilimage)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def rotacao(pilimage, canvas, angle):
    def findoriginpx():
        pixels = pilimage.load()
        orgx = 0
        orgy = 0
        for j in range(pilimage.size[1]):
            for i in range(pilimage.size[0] - 1, 0, -1):
                if pixels[i, j] != 255:
                    orgy = j
        for i in range(pilimage.size[0] - 1, 0, -1):
            for j in range(pilimage.size[1]):
                if pixels[i, j] != 255:
                    orgx = i
        return (orgx, pilimage.size[1] - orgy)

    angle = - angle * math.pi / 180.0  # Converte para radianos
    fsp = findoriginpx()
    matrix = piltomatrix(pilimage)
    mvx = fsp[1] * math.sin(angle) - fsp[0] * math.cos(angle) + fsp[0]
    mvy = - fsp[0] * math.sin(angle) - fsp[1] * math.cos(angle) + fsp[1]
    transformation = np.matrix([[math.cos(angle), - math.sin(angle), mvx],
                                [math.sin(angle), math.cos(angle), mvy],
                                [0, 0, 1]])
    results = transformation * matrix
    clear(pilimage, canvas)
    pixels = pilimage.load()

    for column in range(results.shape[1]):
        ptx = int(results[0, column])
        pty = int(results[1, column])
        try:
            pixels[ptx, pilimage.size[1] - pty] = 0
        except IndexError:
            pass

    canvas._image_tk = ImageTk.PhotoImage(pilimage)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def zoom(pilimage, canvas, vertix1, vertix2):
    minx = min(vertix1[0], vertix2[0])
    miny = min(vertix1[1], vertix2[1])
    maxx = max(vertix1[0], vertix2[0])
    maxy = max(vertix1[1], vertix2[1])
    width = float(maxx - minx)
    height = float(maxy - miny)
    if width > height:
        zoomrate = float(pilimage.size[0]) / width
    else:
        zoomrate = float(pilimage.size[1]) / height
    translacao(pilimage, canvas, - minx, - miny)
    escala(pilimage, canvas, zoomrate, zoomrate)
