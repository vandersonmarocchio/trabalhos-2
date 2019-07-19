#!/bin/bash
# Diogo Alves de Almeida
# RA: 95108

help() {
	echo "-i: instala o simulador Sniper, no diretório sniper"
	echo "-p: instala os programas contido em NPB-OMP 3.3.1, no diretório benchmarks"
	echo "-s: realiza as simulações e grava os resultados no diretório resultados"
	echo "-d: gera os dados adicionais para análise e grava os resultados no diretório mcpat"
	echo "-r: gera o relatório (contido no diretório relatório)"
}

installSniper(){

	if [ -d "$PWD/sniper" ]
	then
		echo "SNIPER JÁ INSTALADO..."
	else
		echo "INSTALANDO SNIPER..."
		echo "FAZENDO DOWNLOAD DO SNIPER E DESCOMPACTANDO ARQUIVO..."
		wget http://snipersim.org/download/05b45c7d14710c5d/packages/sniper-latest.tgz
		tar -vzxf sniper-latest.tgz
		rm -rf sniper-latest.tgz
		rm -rf sniper-7.2-pin2
		mv sniper-7.2/ sniper

		echo "FAZENDO DOWNLOAD DO PIN-3.7, DESCOMPACTANDO E MOVENDO PARA A PASTA DO SNIPER..."
		wget https://software.intel.com/sites/landingpage/pintool/downloads/pin-3.7-97619-g0d0c92f4f-gcc-linux.tar.gz 
		tar -vzxf pin-3.7-97619-g0d0c92f4f-gcc-linux.tar.gz 
		rm -rf pin-3.7-97619-g0d0c92f4f-gcc-linux.tar.gz 
		mv pin-3.7-97619-g0d0c92f4f-gcc-linux sniper/
		mv sniper/pin-3.7-97619-g0d0c92f4f-gcc-linux sniper/pin_kit 

		echo "CONFIGURANDO O APT-GET..."
		sudo dpkg --add-architecture i386

		echo "INSTALANDO PROGRAMAS NECESSÁRIOS E DEPENDÊNCIAS..."
		sudo apt-get install binutils build-essential curl git libboost-dev libbz2-dev libc6:i386 libncurses5:i386 libsqlite3-dev libstdc++6:i386 python wget zlib1g-dev
		sudo apt-get install --reinstall m4 csh g++ gfortran

		echo "COMPILANDO O SNIPER..."
		cd sniper
		make -j 5
		cd ..
		echo "CRIANDO ARQUIVOS DE CONFIGURAÇÃO..."
		makeConfig
	fi
}

installBenchmark(){
	if [ -d "$PWD/sniper" ]
	then
		cd sniper
		if [ -d "$PWD/benchmarks" ]
		then	
			echo "BENCHMARK JÁ INSTALADO..."
		else
			echo "INSTALANDO BENCHMARK..."
			echo "FAZENDO DOWNLOAD DO BENCHMARK E DESCOMPACTANDO ARQUIVO..."
			wget http://snipersim.org/packages/sniper-benchmarks.tbz
			tar xjf sniper-benchmarks.tbz
			rm -rf  sniper-benchmarks.tbz
			cd benchmarks
			export GRAPHITE_ROOT=$OLDPWD
			export BENCHMARKS_ROOT=$PWD

			echo -e "\nCompilando o NPB...\n"					
			cd tools/hooks
			make -j 5
			cd ..
			cd ..
			echo -e "\nCompilando o NPB...\n"
			cd npb
			make -j 5 
			cd .. 
			cd ..
			cd ..
		fi
	else
		echo "NECESSÁRIO A INSTALAÇÃO DO SNIPER PRIMEIRO..."
		installSniper
		installBenchmark
	fi
}

simulation(){
	if [ -d "$PWD/sniper" ]
	then
		cd sniper
		if [ -d "$PWD/benchmarks" ]
		then
			if [ -d "$OLDPWD/resultados" ]
			then 
				echo "SIMULAÇÃO JÁ REALIZADA..."
			else
				echo "CRIANDO PASTA DE RESULTADOS..."
				mkdir -p $OLDPWD/resultados
				
				echo "SIMULANDO..."
				export OMP_NUM_THREADS=8
				cd ..

				for value in bt cg dc ep ft is lu mg sp ua
				do	
					
					echo "INICIANDO ${value^^} COM ARQUITETURA 1"
					cd sniper				
					./run-sniper -n 8 -d $OLDPWD/resultados/${value^^}/arquitetura1 -c $OLDPWD/configuracoes/arquitetura1.cfg $PWD/benchmarks/npb/NPB3.3.1/NPB3.3-OMP/bin/$value.W.x
					cd ..				
					cd resultados/${value^^}/arquitetura1
					mv sim.out ${value^^}.arquitetura1.sim.out
					cd ..
					cd ..
					cd ..

					echo "INICIANDO ${value^^} COM ARQUITETURA 2"
					cd sniper
					./run-sniper -n 8 -d $OLDPWD/resultados/${value^^}/arquitetura2 -c $OLDPWD/configuracoes/arquitetura2.cfg $PWD/benchmarks/npb/NPB3.3.1/NPB3.3-OMP/bin/$value.W.x
					cd ..
					cd resultados/${value^^}/arquitetura2 
					mv sim.out ${value^^}.arquitetura2.sim.out
					cd ..
					cd ..
					cd ..
				done
			fi
		else 
			echo "NECESSÁRIO A INSTALAÇÃO DO BENCHMARK PRIMEIRO..."
			installBenchmark
			simulation
		fi
	else
		echo "NECESSÁRIO A INSTALAÇÃO DO SNIPER E BENCHMARK PRIMEIRO..."
		installSniper
		installBenchmark
		simulation
	fi 
}

mcpat(){
	if [ -d "$PWD/sniper" ]
	then
		cd sniper
		if [ -d "$PWD/benchmarks" ]
		then
			if [ -d "$OLDPWD/resultados" ]
			then
				if [ -d "$OLDPWD/mcpat" ]
				then 
					echo "MCPAT JÁ EXECUTADO..."
				else 
					echo "CRIANDO PASTA MCPAT..."
					cd ..
					mkdir -p mcpat
					echo "EXECUTANDO MCPAT..."
					for value in bt cg dc ep ft is lu mg sp ua
						do	
							echo "INICIANDO MCPAT DO PROGRAMA ${value^^} DA ARQUITETURA 1..."
							cd sniper
							./run-sniper tools/mcpat.py -d $OLDPWD/resultados/${value^^}/arquitetura1/ -o $OLDPWD/resultados/${value^^}/arquitetura1/${value^^}.arquitetura1.mcpat
							mv $OLDPWD/resultados/${value^^}/arquitetura1/${value^^}.arquitetura1.mcpat.png $OLDPWD/mcpat/
							mv $OLDPWD/resultados/${value^^}/arquitetura1/${value^^}.arquitetura1.mcpat.py $OLDPWD/mcpat/
							mv $OLDPWD/resultados/${value^^}/arquitetura1/${value^^}.arquitetura1.mcpat.txt $OLDPWD/mcpat/
							mv $OLDPWD/resultados/${value^^}/arquitetura1/${value^^}.arquitetura1.mcpat.xml $OLDPWD/mcpat/
							cd ..

							echo "INICIANDO MCPAT DO PROGRAMA ${value^^} DA ARQUITETURA 2..."
							cd sniper
							./run-sniper tools/mcpat.py -d $OLDPWD/resultados/${value^^}/arquitetura2/ -o $OLDPWD/resultados/${value^^}/arquitetura2/${value^^}.arquitetura2.mcpat
							mv $OLDPWD/resultados/${value^^}/arquitetura2/${value^^}.arquitetura2.mcpat.png $OLDPWD/mcpat/
							mv $OLDPWD/resultados/${value^^}/arquitetura2/${value^^}.arquitetura2.mcpat.py $OLDPWD/mcpat/
							mv $OLDPWD/resultados/${value^^}/arquitetura2/${value^^}.arquitetura2.mcpat.txt $OLDPWD/mcpat/
							mv $OLDPWD/resultados/${value^^}/arquitetura2/${value^^}.arquitetura2.mcpat.xml $OLDPWD/mcpat/
							cd ..
						done
					fi
			else
				echo "NECESSÁRIO EXECUTAR A SIMULAÇÃO PRIMEIRO..."
				simulation
				mcpat
			fi
		else 
			echo "NECESSÁRIO A INSTALAÇÃO DO BENCHMARK E EXECUTAR A SIMULAÇÃO PRIMEIRO..."
			installBenchmark
			simulation
			mcpat
		fi
	else
		echo "NECESSÁRIO A INSTALAÇÃO DO SNIPER E  BENCHMARK E EXECUTAR SIMULAÇÃO PRIMEIRO..."
		installSniper
		installBenchmark
		simulation
		mcpat
	fi 
}

relatorio(){
	echo "INSTALANDO R SE NECESSÁRIO..."
	sudo apt-get install r-base
	echo "GERANDO GRÁFICOS PELOS ARQUIVOS EM R..."
	cd relatorio
	mkdir -p imagens

	cd r
	Rscript cacheL1_D.r
	Rscript cacheL1_I.r
	Rscript cacheL2.r
	Rscript cacheL3.r
	Rscript ipc.r
	Rscript power.r
	mv $PWD/cacheL1_D.png $OLDPWD/imagens/
	mv $PWD/cacheL1_I.png $OLDPWD/imagens/
	mv $PWD/cacheL2.png $OLDPWD/imagens/
	mv $PWD/cacheL3.png $OLDPWD/imagens/
	mv $PWD/ipc.png $OLDPWD/imagens/
	mv $PWD/power.png $OLDPWD/imagens/
	cd ..
	
	echo "cp -R $PWD/imagens $OLDPWD/latex/"
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
	mv main.pdf $OLDPWD/relatorio_ra95108.pdf
	cd ..

	echo "RELATÓRIO GERADO..."
}


makeConfig(){
	mkdir -p configuracoes
	cd configuracoes
	touch arquitetura1.cfg
	echo "# Configuration file for Xeon X5550 Gainestown
# See http://en.wikipedia.org/wiki/Gainestown_(microprocessor)#Gainestown
# and http://ark.intel.com/products/37106

#include nehalem

[perf_model/core]
frequency = 3.33

[perf_model/l1_icache]
replacement_policy = nmru

[perf_model/l1_dcache]
replacement_policy = nmru

[perf_model/l2_cache]
replacement_policy = nmru

[perf_model/l3_cache]
perfect = false
cache_block_size = 64
cache_size = 8192
associativity = 16
address_hash = mask
replacement_policy = nmru
data_access_time = 30 # 35 cycles total according to membench, +L1+L2 tag times
tags_access_time = 10
perf_model_type = parallel
writethrough = 0
shared_cores = 4

[perf_model/dram_directory]
# total_entries = number of entries per directory controller.
total_entries = 1048576
associativity = 16
directory_type = full_map

[perf_model/dram]
# -1 means that we have a number of distributed DRAM controllers (4 in this case)
num_controllers = -1
controllers_interleaving = 4
# DRAM access latency in nanoseconds. Should not include L1-LLC tag access time, directory access time (14 cycles = 5.2 ns),
# or network time [(cache line size + 2*{overhead=40}) / network bandwidth = 18 ns]
# Membench says 175 cycles @ 2.66 GHz = 66 ns total
latency = 45
per_controller_bandwidth = 7.6              # In GB/s, as measured by core_validation-dram
chips_per_dimm = 8
dimms_per_controller = 4
type = normal

[network]
memory_model_1 = bus
memory_model_2 = bus

[network/bus]
bandwidth = 25.6 # in GB/s. Actually, it's 12.8 GB/s per direction and per connected chip pair
ignore_local_traffic = true # Memory controllers are on-chip, so traffic from core0 to dram0 does not use the QPI links" >> arquitetura1.cfg
	touch arquitetura2.cfg
	echo "# Configuration file for Xeon X5550 Gainestown
# See http://en.wikipedia.org/wiki/Gainestown_(microprocessor)#Gainestown
# and http://ark.intel.com/products/37106

#include nehalem

[perf_model/core]
frequency = 3.33

[perf_model/l1_icache]
replacement_policy = lru

[perf_model/l1_dcache]
replacement_policy = lru

[perf_model/l2_cache]
replacement_policy = lru

[perf_model/l3_cache]
perfect = false
cache_block_size = 64
cache_size = 8192
associativity = 16
address_hash = mask
replacement_policy = lru
data_access_time = 30 # 35 cycles total according to membench, +L1+L2 tag times
tags_access_time = 10
perf_model_type = parallel
writethrough = 0
shared_cores = 4

[perf_model/dram_directory]
# total_entries = number of entries per directory controller.
total_entries = 1048576
associativity = 16
directory_type = full_map

[perf_model/dram]
# -1 means that we have a number of distributed DRAM controllers (4 in this case)
num_controllers = -1
controllers_interleaving = 4
# DRAM access latency in nanoseconds. Should not include L1-LLC tag access time, directory access time (14 cycles = 5.2 ns),
# or network time [(cache line size + 2*{overhead=40}) / network bandwidth = 18 ns]
# Membench says 175 cycles @ 2.66 GHz = 66 ns total
latency = 45
per_controller_bandwidth = 7.6              # In GB/s, as measured by core_validation-dram
chips_per_dimm = 8
dimms_per_controller = 4
type = normal

[network]
memory_model_1 = bus
memory_model_2 = bus

[network/bus]
bandwidth = 25.6 # in GB/s. Actually, it's 12.8 GB/s per direction and per connected chip pair
ignore_local_traffic = true # Memory controllers are on-chip, so traffic from core0 to dram0 does not use the QPI links" >> arquitetura2.cfg
	cd ..
}

opt=$1
case $opt in
	-h) help ;;
	-i) installSniper ;;
	-p) installBenchmark ;;
	-s) simulation ;;
	-d) mcpat ;;
	-r) relatorio ;;
	*) echo "ENTRADA INCORRETA!"
	exit ;;
esac
