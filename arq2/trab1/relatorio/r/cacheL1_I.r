file_name <- "cacheL1_I.png"
title <- "Aumento da Taxa de Miss da Cache L1-I em relação a Arquitetura 1"
x_label <- "Programas"
y_label <- "% de aumento"

arquitetura1 <- c(1.695, -0.602, 5.263, 0.0, 44.681, 0.0, 14.535, 0.0, 13.714, 9.756)

data=matrix(arquitetura1, nrow=1)

colnames(data) = c("BT", "CG", "DC", "EP", "FT", "IS", "LU", "MG", "SP", "UA")
rownames(data) = c("Arquitetura 2")

png(filename = file_name, width = 800, height = 600)
par(mar=c(6,8,4,4))
cor <- c("gold")

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
         font.lab=2)

legend("topright", rownames(data), fill = cor , bty = "n")
text(x, y = data, label = data, pos = 4, offset=0, cex = 1.0, col = "black", srt = 90)
title(ylab=y_label, line=5, cex.lab=1.3, font.lab=2)
