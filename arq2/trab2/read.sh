cd resultados

cd MeshXY
for value in is
    do
        cd $value
        cat stats.txt | grep average_flit_network_latency | awk {'print $1 , $2'} >$valueMeshXY.txt
        cat stats.txt | grep average_hops | awk {'print $1 , $2'} >>$valueMeshXY.txt
        cat stats.txt | grep bw_write::total | awk {'print $1 , $2'} >>$valueMeshXY.txt
        cat stats.txt | grep bw_read::total | awk {'print $1 , $2'} >>$valueMeshXY.txt
        cat stats.txt | grep averagePower | awk {'print $1 , $2'} >>$valueMeshXY.txt
        cd ..
    done

cd ..

cd Octagon
for value in is
    do
        cd $value
       	cat stats.txt | grep average_flit_network_latency | awk {'print $1 , $2'} >$valueOctagon.txt
        cat stats.txt | grep average_hops | awk {'print $1 , $2'} >>$valueOctagon.txt
        cat stats.txt | grep bw_write::total | awk {'print $1 , $2'} >>$valueOctagon.txt
        cat stats.txt | grep bw_read::total | awk {'print $1 , $2'} >>$valueOctagon.txt
        cat stats.txt | grep averagePower | awk {'print $1 , $2'} >>$valueOctagon.txt
        cd ..
    done
cd ..
