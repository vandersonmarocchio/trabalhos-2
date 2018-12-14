import tkinter as Tkinter
import queue as Queue
import math
from PIL import Image, ImageDraw, ImageTk
import transformacoes

CANVASWIDTH = 512
CANVASHEIGHT = 512

class WaitingEv(object):
    def __init__(self): 
        self.widget1 = Tkinter.Frame(None)
        self.widget1.pack()
        self.msg = Tkinter.Label(self.widget1, text="Diogo Alves de Almeida (RA = 95108)")
        self.msg.pack ()
        self.clickstack = None
        self.waiting_clicks = 0
        self.waiting_func = None
        self.canvas = None
        self.img = None

    def set_waitingf(self, waiting_clicks, waiting_func):
        self.waiting_clicks = waiting_clicks
        self.waiting_func = waiting_func
        self.clickstack = Queue.Queue()

    def call_waitingf(self):
        self.waiting_func(self.clickstack, self.canvas, self.img)
        self.waiting_func = None

    def new_click(self, x, y):
        if self.waiting_clicks > 0:
            self.clickstack.put((x, y))
            self.waiting_clicks -= 1
            if self.waiting_clicks == 0:
                self.call_waitingf()


def pegaPonto(event, eventmng):
    click_x, click_y = event.x, event.y
    eventmng.new_click(click_x, click_y)


def desenhaLinha(clickstack, canvas, img):
    src = clickstack.get_nowait()
    dst = clickstack.get_nowait()
    image_draw = ImageDraw.Draw(img)
    image_draw.line([src, dst], fill='black', width=1)
    canvas._image_tk = ImageTk.PhotoImage(img)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def desenhaReta(clickstack, canvas, img):
    vx1 = clickstack.get_nowait()
    vx2 = clickstack.get_nowait()
    image_draw = ImageDraw.Draw(img)
    image_draw.rectangle([vx1, vx2], outline='black')
    canvas._image_tk = ImageTk.PhotoImage(img)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)

def zoom(clickstack, canvas, img):
    vx1 = clickstack.get_nowait()
    vx2 = clickstack.get_nowait()
    transformacoes.zoom(img, canvas, vx1, vx2)


def desenhaTriangulo(clickstack, canvas, img):
    vx1 = clickstack.get_nowait()
    vx2 = clickstack.get_nowait()
    vx3 = clickstack.get_nowait()
    image_draw = ImageDraw.Draw(img)
    image_draw.polygon([vx1, vx2, vx3], outline='black')
    canvas._image_tk = ImageTk.PhotoImage(img)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def desenhaCirculo(clickstack, canvas, img):
    center = clickstack.get_nowait()
    crange = clickstack.get_nowait()
    radius = math.sqrt(math.pow(crange[0] - center[0], 2) +
                       math.pow(crange[1] - center[1], 2))
    radius = int(radius)
    vx1 = (center[0] + radius, center[1] + radius)
    vx2 = (center[0] - radius, center[1] - radius)
    image_draw = ImageDraw.Draw(img)
    image_draw.ellipse([vx2, vx1], outline='black')
    canvas._image_tk = ImageTk.PhotoImage(img)
    canvas.itemconfigure(canvas._image_id, image=canvas._image_tk)


def clear(img, canvas):
    transformacoes.clear(img, canvas)

def janelaAjuda():
    top = Tkinter.Toplevel(width=500, height=500)
    top.title('Ajuda')

    msg = Tkinter.Message(top, text="Modo de usar: \n 1) Para desenhar selecione a opção desejada e clique na área de desenho: \n \t I - Triângulo: clique 3 vezes na área de desenho para definir os vértices do triângulo; \n \t II - Retângulo: clique 2 vezes na área de desenho para definir a diagonal do retângulo; \n \t III - Círculo: clique 2 vezes na área de desenho, a primeira para definir o centro do circulo e a segunda vez para definir a distancia do raio; \n \t IV - Linha: clique 2 vezes na tela para definir as extremidades da linha. \n 2) Para limpar basta clicar no botão 'Limpar' que limpará toda a área de desenho. \n 3) Para transladar os objetos basta clicar no botão 'Translação' que abrirá uma tela onde você deverá colocar os valores de X e Y que serão transladados.\n 4) Para mudança de escala basta clicar no botão 'Escala' que abrirá uma tela onde você deverá colocar os valores de X e Y para a mudança de escala. \n 5) Para a rotação basta clicar no botão 'Rotação' que abrirá uma tela onde você deverá colocar o valor para rotacionar em graus. \n 6) Para realizar o zoom é necessário clicar 2 vezes na área de desenho onde é para definir a diagonal da janela onde será o zoom.")
    msg.pack()


def realizaTranslacao(img, canvas):
    def press(xtext, ytext):
        top.destroy()
        transformacoes.translacao(img, canvas, int(xtext), int(ytext))

    top = Tkinter.Toplevel(width=500, height=500)
    top.title('Translação')

    msg = Tkinter.Message(top, text='Coordenadas de Translação:')
    msg.pack()

    xlabel = Tkinter.Label(top, text='X:')
    xentry = Tkinter.Entry(top)
    xentry.insert(0, '0')
    ylabel = Tkinter.Label(top, text='Y:')
    yentry = Tkinter.Entry(top)
    yentry.insert(0, '0')

    xlabel.pack()
    xentry.pack()
    ylabel.pack()
    yentry.pack()

    button = Tkinter.Button(
        top, text="Transladar", command=lambda: press(
            xentry.get(), yentry.get()))
    button.pack()


def realizaEscala(img, canvas):
    def press(xtext, ytext):
        top.destroy()
        transformacoes.escala(img, canvas, float(xtext), float(ytext))

    top = Tkinter.Toplevel(width=500, height=500)
    top.title('Mudança de Escala')

    msg = Tkinter.Message(top, text='Taxas de Transformação:')
    msg.pack()

    xlabel = Tkinter.Label(top, text='X:')
    xentry = Tkinter.Entry(top)
    xentry.insert(0, '1.0')
    ylabel = Tkinter.Label(top, text='Y:')
    yentry = Tkinter.Entry(top)
    yentry.insert(0, '1.0')

    xlabel.pack()
    xentry.pack()
    ylabel.pack()
    yentry.pack()

    button = Tkinter.Button(
        top, text="Mudar Escala", command=lambda: press(
            xentry.get(), yentry.get()))
    button.pack()


def realizaRotacao(img, canvas):
    def press(atext):
        top.destroy()
        transformacoes.rotacao(img, canvas, float(atext))

    top = Tkinter.Toplevel(width=500, height=500)
    top.title('Rotação')

    msg = Tkinter.Message(top, text=('Ângulo em graus (colocar negativo '
                                     'para giro anti-horário):'))
    msg.pack()

    alabel = Tkinter.Label(top, text='X:')
    aentry = Tkinter.Entry(top)
    aentry.insert(0, '0.0')

    alabel.pack()
    aentry.pack()

    button = Tkinter.Button(
        top, text="Rotacionar", command=lambda: press(
            aentry.get()))
    button.pack()


def main():
    root = Tkinter.Tk()
    root.title('Trabalho 1 de CG - 2018')
    eventmng = WaitingEv()

    canvas = Tkinter.Canvas(width=CANVASWIDTH, height=CANVASHEIGHT)
    eventmng.canvas = canvas
    image = Image.new('1', (720, 720), '#ffffff')
    eventmng.img = image
    drawpane = Tkinter.Frame(root)
    transpane = Tkinter.Frame(root)
    ajuda = Tkinter.Frame(root)

    btn_clear = Tkinter.Button(transpane, text="Limpar",
                                 command=lambda: clear(image, canvas))
    btn_linha = Tkinter.Button(drawpane, text="Linha",
                                command=lambda: eventmng.set_waitingf(
                                    2, desenhaLinha))
    btn_retangulo = Tkinter.Button(drawpane, text="Retângulo",
                                command=lambda: eventmng.set_waitingf(
                                    2, desenhaReta))
    btn_zoom = Tkinter.Button(transpane, text="Zoom",
                                command=lambda: eventmng.set_waitingf(
                                    2, zoom))
    btn_triangulo = Tkinter.Button(drawpane, text="Triângulo",
                                 command=lambda: eventmng.set_waitingf(
                                     3, desenhaTriangulo))
    btn_circulo = Tkinter.Button(drawpane, text="Círculo",
                                  command=lambda: eventmng.set_waitingf(
                                      2, desenhaCirculo))
    btn_rotacao = Tkinter.Button(transpane, text="Rotação",
                                  command=lambda: realizaRotacao(image, canvas))
    btn_escala = Tkinter.Button(transpane, text="Escala",
                                 command=lambda: realizaEscala(image, canvas))
    btn_translacao = Tkinter.Button(transpane, text="Translação",
                                command=lambda: realizaTranslacao(image, canvas))
    btn_ajuda = Tkinter.Button(transpane, text="Ajuda (?)",
                                command=lambda: janelaAjuda())
    
    
    drawpane.pack(side=Tkinter.TOP)
    canvas.pack(side=Tkinter.TOP)
    transpane.pack(side=Tkinter.BOTTOM)
    btn_linha.pack(side=Tkinter.LEFT)
    btn_retangulo.pack(side=Tkinter.RIGHT)
    btn_circulo.pack(side=Tkinter.RIGHT)
    btn_triangulo.pack(side=Tkinter.RIGHT)
    btn_clear.pack(side=Tkinter.RIGHT)
    btn_zoom.pack(side=Tkinter.RIGHT)
    btn_rotacao.pack(side=Tkinter.RIGHT)
    btn_escala.pack(side=Tkinter.RIGHT)
    btn_translacao.pack(side=Tkinter.RIGHT)
    btn_ajuda.pack(side=Tkinter.BOTTOM)

    canvas._image_tk = ImageTk.PhotoImage(image)
    canvas._image_id = canvas.create_image(0, 0, image=canvas._image_tk, anchor='nw')
    canvas.tag_bind(canvas._image_id, "<Button-1>", lambda e: pegaPonto(e, eventmng))

    root.mainloop()

if __name__ == "__main__":
    main()
