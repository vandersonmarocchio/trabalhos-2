#!/usr/bin/env python
# -*- coding: utf-8 -*-

""" 
    app.py: Trabalho de Sistemas Hipermídia e Multimídia.
    
    Instrução para execução: python app.py text.txt
                             python app.py code.morse
                             python app.py audio.wav
"""

__author__ = "Diogo Alves de Almeida RA95108"

import sys
import re # Python RegEx
import math
import numpy as np
import scipy.io.wavfile

alfabeto_binario = {
    ' ' : '0',
    '.' : '10',
    '-' : '1110'
}

alfabeto_morse = {
    ''      : ' ',
    '.-'    : 'A',
    '-...'  : 'B',
    '-.-.'  : 'C',
    '-..'   : 'D',
    '.'     : 'E',
    '..-.'  : 'F',
    '--.'   : 'G',
    '....'  : 'H',
    '..'    : 'I',
    '.---'  : 'J',
    '-.-'   : 'K',
    '.-..'  : 'L',
    '--'    : 'M',
    '-.'    : 'N',
    '---'   : 'O',
    '.--.'  : 'P',
    '--.-'  : 'Q',
    '.-.'   : 'R',
    '...'   : 'S',
    '-'     : 'T',
    '..-'   : 'U',
    '...-'  : 'V',
    '.--'   : 'W',
    '-..-'  : 'X',
    '-.--'  : 'Y',
    '--..'  : 'Z',
    '-----' : '0',
    '.----' : '1',
    '..---' : '2',
    '...--' : '3',
    '....-' : '4',
    '.....' : '5',
    '-....' : '6',
    '--...' : '7',
    '---..' : '8',
    '----.' : '9',
    '--..--': ', ',
    '.-.-.-': '.',
    '..--..': '?',
    '-..-.' : '/',
    '-....-': '-',
    '-.--.' : '(',
    '-.--.-': ')'
}

def getMorse(letra):
    for codigo in alfabeto_morse:
        if(alfabeto_morse[codigo] in letra):
            return codigo

def letras_para_morse(linha):
    decodificado = ''
    palavras = re.split('\s', linha)
    for palavra in palavras:
        for letra in palavra:
            decodificado += getMorse(letra) + "  "
        decodificado+= "    "
    return decodificado

def morse_para_letras(morse):
    decodificado = ''
    morse = morse.replace("  ", " ")
    linha = re.split('\s', morse)
    for letra in linha:
        decodificado += alfabeto_morse.get(letra)
    return decodificado

def morse_para_binario(morse):
    decodificado = ''
    for carac in morse:
        decodificado += alfabeto_binario.get(carac)
    return decodificado

def morse_para_audio(morse):
    frequency = 440
    sound_unit = 0.25
    sampling_rate = 48000
    audio = []
    num_samples = int(sampling_rate * sound_unit)

    for char in morse:
        if char == '.':
            audio.append([np.sin(2 * np.pi * frequency * x / sampling_rate) for x in range(num_samples)])
        elif char == '-':
            audio.append([np.sin(2 * np.pi * frequency * x / sampling_rate) for x in range(num_samples)])
            audio.append([np.sin(2 * np.pi * frequency * x / sampling_rate) for x in range(num_samples)])
            audio.append([np.sin(2 * np.pi * frequency * x / sampling_rate) for x in range(num_samples)])
        else:
            audio.append([0] * num_samples)
            audio.append([0] * num_samples)
        audio.append([0] * num_samples)

    audio = np.reshape(audio, -1)
    scipy.io.wavfile.write('audio.wav', sampling_rate, audio)

def entradaTexto(nomeArquivo):
    with open(nomeArquivo) as arquivo_entrada:
        saida1 = open("code.morse", "w")
        texto = ''
        for linha in arquivo_entrada:
            linha = linha.replace("\n", "")
            string = letras_para_morse(linha)
            texto += string
            morse_para_letras(string)
            saida1.write(morse_para_binario(string)+ "\n")
        saida1.close()

def entradaMorse(nomeArquivo):
    with open(nomeArquivo) as arquivo_entrada:
        saida1 = open("text.txt", "w")
        texto = ''
        morse = ''
        for linha in arquivo_entrada:
            linha = linha.replace("000000\n", "")
            linha = linha.replace("1110", "-")
            linha = linha.replace("10", ".")
            linha = linha.replace("00", " ")
            linha = linha.replace("\n", " ")
            # print(re.split('\s\s\s', linha))
            morse += linha
            texto += letras_para_morse(morse_para_letras(linha))
            saida1.write(morse_para_letras(linha)+ "\n")
        print(morse)
        morse_para_audio(morse)
        saida1.close()

def main(nomeArquivo):
    if (nomeArquivo[-4:] in ".txt"):
        entradaTexto(nomeArquivo)
    elif (nomeArquivo[-4:] in ".morse"):
        entradaMorse(nomeArquivo)
    else:
        print("Formato de arquivo incorreto! (Entre com .txt .morse .wav)")

if __name__ == "__main__":
    main(sys.argv[1])