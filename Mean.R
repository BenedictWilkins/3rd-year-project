GroupedMeans <- function(x) {
  agg <- aggregate(KWH ~ DateTime, x, as.vector)
  Mean <- sapply(agg[,"KWH"], mean)
  cbind(agg,Mean)
}

GroupDays <- function(x) {
  xf <- within(x, DateTime<-data.frame(do.call('rbind', strsplit(as.character(DateTime), ' ', fixed=TRUE))))
  agg <- aggregate(KWH ~ DateTime$X2, xf, as.vector)
  Mean <- sapply(agg[,"KWH"], mean)
  Std <- sapply(agg[,"KWH"], sd)
  cbind(agg,Mean,Std)
}

#read data
pn <- read.csv("Power-Networks-LCL-June2015-1.csv", header=T, stringsAsFactors = F)

#change name of column
colnames(pn)[4] <- "KWH"

#remove invalid values
pn[pn$KWH == "Null", "KWH"] <- NA
pn$KWH <- sapply(pn$KWH, as.numeric)
pn <- na.omit(pn)

#split data set into groups
split <- split(pn, pn$Acorn_grouped)

#plot showing seasonality
groupedMeans <- lapply(split, function(x) GroupedMeans(x))
groupedMeans$Affluent$DateTime <- strptime(groupedMeans$Affluent$DateTime, "%Y-%m-%d %H:%M:%S")
msq <- seq(1, length(groupedMeans$Affluent$Mean), 24)
x <- groupedMeans$Affluent$DateTime[msq]
y <- groupedMeans$Affluent$Mean[msq]
plot(x,y, xlab="Date/Time", ylab="KWH per half hour")
lo <- lowess(x,y,f=3/10)
lines(lo, col='red', lwd=2)

groupedDay <- lapply(split, function(x) GroupDays(x))

plot(as.numeric(groupedDay$`ACORN-U`$`DateTime$X2`),groupedDay$`ACORN-U`$Mean, ylab="Mean KWH per half hour", xlab="Time in halfhour intervals")
d1 <- dnorm(seq(0,4,length=48),1.8,0.9)
#lines(d1, type="l")
d2 <- dnorm(seq(0,6,length=48),5.3,0.8)
#lines(d2, type="l")
d3 <- d1 + d2
lines(d3, type="l")

plot(as.numeric(groupedDay$Affluent$`DateTime$X2`),groupedDays$Affluent$Mean)
plot(as.numeric(groupedDay$Adversity$`DateTime$X2`),groupedDay$Adversity$Mean)
plot(as.numeric(groupedDayComfortable$`DateTime$X2`),groupedDay$Comfortable$Mean)

mean(groupedDay$Affluent$KWH[[1]])
plot(dnorm(seq(-4,4, length = 100),0,0.5), type="l")

write.table(split$`ACORN-U`, "~/RWorkspace/3rd year project data analysis/acorn-u.txt", sep=" ", row.names=FALSE)
write.table(split$Adversity, "~/RWorkspace/3rd year project data analysis/adversity.txt", sep=" ", row.names=FALSE)
write.table(split$Affluent, "~/RWorkspace/3rd year project data analysis/affluent.txt", sep=" ", row.names=FALSE)
write.table(split$Comfortable, "~/RWorkspace/3rd year project data analysis/comfortable.txt", sep=" ", row.names=FALSE)

write.table(groupedDay$`ACORN-U`[,-2], "~/RWorkspace/3rd year project data analysis/gmAcorn-u.txt", sep=" ", row.names=FALSE)
write.table(groupedDay$Adversity[,-2], "~/RWorkspace/3rd year project data analysis/gmAdversity.txt", sep=" ", row.names=FALSE)
write.table(groupedDay$Affluent[,-2], "~/RWorkspace/3rd year project data analysis/gmAffluent.txt", sep=" ", row.names=FALSE)
write.table(groupedDay$Comfortable[,-2], "~/RWorkspace/3rd year project data analysis/gmComfortable.txt", sep=" ", row.names=FALSE)




