file_name <- "is.png"
title <- "Porcentagem de aumento em relação ao MeshXY do algoritmo IS"
x_label <- "Dados avaliados"
y_label <- "% de aumento"

arquitetura1 <- c(-16.6994796556, -35.6488491088, 37.1341513756)

data=matrix(arquitetura1, nrow=1)
colnames(data) = c("Latência", "Hops", "Average Power")
rownames(data) = c("Octagon")

png(filename = file_name, width = 800, height = 600)
par(mar=c(6,8,4,4))
cor <- c("deepskyblue3")

x <- barplot(data,
         main=title,
         xlab=x_label,
         ylim=c(min(data),1.2*max(data)),
         col=cor,
         border="white",
         font.axis=1,
         beside=T,
         cex.lab=1.3,
         las=1,
         font.lab=2,
         axes=FALSE)

legend("topright", rownames(data), fill = cor , bty = "n")
text(x, y = data, label = data, pos = 4, offset=0, cex = 1.0, col = "black", srt = 90)
title(ylab=y_label, line=5, cex.lab=1.3, font.lab=2)
