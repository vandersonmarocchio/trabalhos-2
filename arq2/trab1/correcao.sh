cd resultados
	for value in bt cg dc ep ft is lu mg sp ua
	do					
		cd ${value^^}
		cd arquitetura1
		ls
		cd ..

		cd arquitetura2
		ls
		cd ..
		cd ..	
	
	done