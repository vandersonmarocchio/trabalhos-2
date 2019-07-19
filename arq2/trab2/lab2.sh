#!/bin/bash
# Diogo Alves de Almeida RA:95108
# Cristofer Alexandre Oswald RA: 94509
# Evandro Fiorim Estima RA: 84757

help() {
	echo "-i: instala o simulador gem5, no diretório gem5"
	echo "-b: instala os programas contido em CAPBenchmark, no diretório CAPBenchmarks"
	echo "-s: realiza as simulações e grava os resultados no diretório resultados"
	echo "-d: gera os dados adicionais para análise e grava os resultados no diretório mcpat"
	echo "-r: gera o relatório (contido no diretório relatório)"
}

installGem5(){
	if [ -d "$PWD/gem5" ]
	then
		echo "gem5 JÁ INSTALADO..."
	else
		echo "INSTALANDO DEPENDÊNCIAS..."
		sudo apt install build-essential git m4 scons zlib1g zlib1g-dev libprotobuf-dev protobuf-compiler libprotoc-dev libgoogle-perftools-dev python-dev python
		sudo apt-get install automake
		sudo apt-get install libboost-all-dev
		echo "INSTALANDO gem5..."
		echo "CLONANDO DIRETÓRIO DO gem5..."
		git clone https://gem5.googlesource.com/public/gem5
		echo "CRIANDO X86_MOESI_CMP_directory"
		touch X86_MOESI_CMP_directory
		echo "TARGET_ISA = 'x86'
CPU_MODELS = 'AtomicSimpleCPU,TimingSimpleCPU,O3CPU,MinorCPU'
PROTOCOL='MOESI_CMP_directory'" >> X86_MOESI_CMP_directory
		echo "MOVENDO X86_MOESI_CMP_directory PARA A PASTA build_opts"
		mv X86_MOESI_CMP_directory gem5/build_opts
		cd gem5
		echo "COMPILANDO gem5..."
		scons build/X86_MOESI_CMP_directory/gem5.fast -j 9
		cd configs/topologies
        echo "CRIANDO TOPOLOGIA OCTAGON..."
		makeOctagon
		sed -i '105d' Mesh_XY.py
	fi
}

installBenchmark(){
	if [ -d "$PWD/gem5" ]
	then
		cd gem5
		if [ -d "$PWD/CAPBench" ]
		then
			echo "BENCHMARK JÁ INSTALADO..."
		else
			echo "INSTALANDO BENCHMARK..."
			echo "CLONANDO DIRETÓRIO DO BENCHMARK"
			git clone https://github.com/cart-pucminas/CAPBenchmarks.git
			echo "COMPILANDO BENCHMARK..."
			cd CAPBenchmarks/gem5/src/TSP
            sed -i 's/inline//g' defs.c
            sed -i 's/inline//g' defs.h
            sed -i 's/inline//g' job.c
            sed -i 's/inline//g' tsp.c
            sed -i 's/inline//g' tsp.h
            cd ..
            cd ..
            make all
			echo "MOVENDO BIN PARA A PASTA CAPBench..."
			cp -rf bin/ ./../../CAPBench
		fi
	else
		echo "NECESSÁRIO A INSTALAÇÃO DO gem5 PRIMEIRO..."
		installGem5
		installBenchmark
	fi
}

simulation(){
	if [ -d "$PWD/gem5" ]
	then
		cd gem5
		if [ -d "$PWD/CAPBench" ]
		then
			if [ -d "$PWD/resultados" ]
			then
				echo "SIMULAÇÃO JÁ REALIZADA..."
			else
				echo "CRIANDO PASTA DE RESULTADOS..."
				mkdir -p $PWD/resultados
				cd resultados
				mkdir -p MeshXY
				mkdir -p Octagon
				cd ..
				for value in fast fn gf is km lu tsp
				do
					echo "INICIANDO MESHXY $value"
					build/X86_MOESI_CMP_directory/gem5.fast --outdir=../gem5/resultados/MeshXY/$value configs/example/se.py -c CAPBench/$value '--options=--class small --nthreads 16' --ruby --cpu-clock=3GHz --ruby-clock=3GHz --mem-size=1GB --network=garnet2.0 --num-l2caches=16 --mesh-rows=4 --num-cpus=16 --topology=Mesh_XY --cpu-type=DerivO3CPU --num-dirs=16 --l1d_size=32kB --l1i_size=32kB --l2_size=512kB --l2_assoc=16 --l1d_assoc=4 --l1i_assoc=4 --garnet-deadlock-threshold=100000

					echo "INICIANDO OCTAGON $value"
					build/X86_MOESI_CMP_directory/gem5.fast --outdir=../gem5/resultados/Octagon/$value configs/example/se.py -c CAPBench/$value '--options=--class small --nthreads 16' --ruby --cpu-clock=3GHz --ruby-clock=3GHz --mem-size=1GB --network=garnet2.0 --num-l2caches=16 --num-cpus=16 --topology=Octagon --cpu-type=DerivO3CPU --l1d_size=32kB --l1i_size=32kB --l2_size=512kB --l2_assoc=16 --l1d_assoc=4 --l1i_assoc=4 --garnet-deadlock-threshold=1000000
				done
			fi
		else
			echo "NECESSÁRIO A INSTALAÇÃO DO BENCHMARK PRIMEIRO..."
			installBenchmark
			simulation
		fi
	else
		echo "NECESSÁRIO A INSTALAÇÃO DO gem5 E BENCHMARK PRIMEIRO..."
		installGem5
		installBenchmark
		simulation
	fi
}

relatorio(){
	echo "INSTALANDO R SE NECESSÁRIO..."
	sudo apt-get install r-base
	echo "GERANDO GRAFICOS EM R"
	cd relatorio
	mkdir -p imagens
	cd r
	Rscript is.r
	Rscript is2.r
	mv $PWD/is.png $OLDPWD/imagens/
	mv $PWD/is2.png $OLDPWD/imagens/
	cd ..

	cp -R $PWD/imagens $PWD/latex/
	cd latex
	pdflatex main.tex
	bibtex main.aux
	pdflatex main.tex
	pdflatex main.tex
	rm -rf main.aux
	rm -rf main.bbl
	rm -rf main.log
	rm -rf main.blg
	rm -rf main.log
	mv main.pdf $OLDPWD/relatorio.pdf
	cd ..

  echo "RELATÓRIO GERADO..."
}

makeOctagon(){
	touch Octagon.py
	echo "#coding: utf-8
# Copyright (c) 2010 Advanced Micro Devices, Inc.
#               2016 Georgia Institute of Technology
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met: redistributions of source code must retain the above copyright
# notice, this list of conditions and the following disclaimer;
# redistributions in binary form must reproduce the above copyright
# notice, this list of conditions and the following disclaimer in the
# documentation and/or other materials provided with the distribution;
# neither the name of the copyright holders nor the names of its
# contributors may be used to endorse or promote products derived from
# this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
# Authors: Brad Beckmann
#          Tushar Krishna

from __future__ import print_function
from __future__ import absolute_import

from m5.params import *
from m5.objects import *

from common import FileSystemConfig

from .BaseTopology import SimpleTopology

# Creates a generic Mesh assuming an equal number of cache
# and directory controllers.
# XY routing is enforced (using link weights)
# to guarantee deadlock freedom.

class Octagon(SimpleTopology):
    description='Octagon'

    def __init__(self, controllers):
        self.nodes = controllers

    def makeTopology(self, options, network, IntLink, ExtLink, Router):
        nodes = self.nodes

        num_routers = options.num_cpus

        # default values for link latency and router latency.
        # Can be over-ridden on a per link/router basis
        link_latency = options.link_latency # used by simple and garnet
        router_latency = options.router_latency # only used by garnet

        # Create the routers in the mesh
        routers = [Router(router_id=i, latency = router_latency) \
            for i in range(num_routers)]
        network.routers = routers

        # link counter to set unique link ids
        link_count = 0

        # First determine which nodes are cache cntrls vs. dirs vs. dma
        cachel1_nodes = []
        cachel2_nodes = []
        dir_nodes = []
        dma_nodes = []
        for node in nodes:
            if node.type == 'L1Cache_Controller':
                cachel1_nodes.append(node)
            elif node.type == 'L2Cache_Controller':
                cachel2_nodes.append(node)
            elif node.type == 'Directory_Controller':
                dir_nodes.append(node)
            elif node.type == 'DMA_Controller':
                dma_nodes.append(node)

        # Connect each node to the appropriate router
        ext_links = []
        for (i, n) in enumerate(cachel1_nodes):
            ext_links.append(ExtLink(link_id=link_count, ext_node=n,
                                    int_node=routers[i],
                                    latency = link_latency))
            link_count += 1

        for (i, n) in enumerate(cachel2_nodes):
            ext_links.append(ExtLink(link_id=link_count, ext_node=n,
                                    int_node=routers[i],
                                    latency = link_latency))
            link_count += 1

        # Connect each dir node to the appropriate router
        for (i, n) in enumerate(dir_nodes):
            ext_links.append(ExtLink(link_id=link_count, ext_node=n,
                                    int_node=routers[i],
                                    latency = link_latency))
            link_count += 1

        # Connect the remainding nodes These should only be DMA nodes.
        rid = 0
        for node in dma_nodes:
            ext_links.append(ExtLink(link_id=link_count, ext_node=node,
                                    int_node=routers[rid],
                                    latency = link_latency))
            link_count += 1
            rid += 2

            if (rid >= num_routers):
                rid = 0

        network.ext_links = ext_links

        # Create the internal links
        int_links = []

        num_octagons = num_routers / 8
        octagon_id = 0


        for octagon in range(num_octagons):

            half = 0
            for router in range(num_routers/num_octagons):

                #Ligação com os vizinhos
                int_links.append(IntLink(link_id=link_count,
                                             src_node=routers[(router%8) + octagon_id*8],
                                             dst_node=routers[((router+1)%8) + octagon_id*8],
                                             src_outport="East",
                                             dst_inport="West",
                                             latency = link_latency,
                                             weight=1))

                int_links.append(IntLink(link_id=link_count,
                                             src_node=routers[((router+1)%8) + octagon_id*8],
                                             dst_node=routers[(router%8) + octagon_id*8],
                                             src_outport="West",
                                             dst_inport="East",
                                             latency = link_latency,
                                             weight=1))
                link_count += 2

                #Ligação com os opostos
                if (half < 4) :
                    int_links.append(IntLink(link_id=link_count,
                                                src_node=routers[(router%8) + octagon_id*8],
                                                dst_node=routers[((router+4)%8) + octagon_id*8],
                                                src_outport="East",
                                                dst_inport="West",
                                                latency = link_latency,
                                                weight=1))

                    int_links.append(IntLink(link_id=link_count,
                                                src_node=routers[((router+4)%8) + octagon_id*8],
                                                dst_node=routers[(router%8) + octagon_id*8],
                                                src_outport="West",
                                                dst_inport="East",
                                                latency = link_latency,
                                                weight=1))
                    link_count += 2
                    half += 1

            octagon_id += 1
            print("\n")

        if (num_octagons > 1):
            octagon_id = 1
            router = 0
            for octagon in range(num_octagons - 1):

                # Ligação com os outros octagons
                int_links.append(IntLink(link_id=link_count,
                                            src_node=routers[router%8],
                                            dst_node=routers[((router+4)%8) + octagon_id*8],
                                            src_outport="North",
                                            dst_inport="South",
                                            latency = link_latency,
                                            weight=1))

                int_links.append(IntLink(link_id=link_count,
                                            src_node=routers[((router+4)%8) + octagon_id*8],
                                            dst_node=routers[router%8],
                                            src_outport="South",
                                            dst_inport="North",
                                            latency = link_latency,
                                            weight=1))
                link_count += 2

                octagon_id += 1
                router += 1

        network.int_links = int_links

    # Register nodes with filesystem
    def registerTopology(self, options):
        for i in xrange(options.num_cpus):
            FileSystemConfig.register_node([i],
                    MemorySize(options.mem_size) / options.num_cpus, i)" >>Octagon.py
}

opt=$1
case $opt in
	-h) help ;;
	-i) installGem5 ;;
	-b) installBenchmark ;;
	-s) simulation ;;
	-r) relatorio ;;
	*) echo "ENTRADA INCORRETA!"
	exit ;;
esac
