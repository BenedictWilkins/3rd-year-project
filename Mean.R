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

write.table(split$`ACORN-U`, "./acorn-u.txt", sep=" ", row.names=FALSE)
write.table(split$Adversity, "./adversity.txt", sep=" ", row.names=FALSE)
write.table(split$Affluent, "./affluent.txt", sep=" ", row.names=FALSE)
write.table(split$Comfortable, "./comfortable.txt", sep=" ", row.names=FALSE)

write.table(groupedDay$`ACORN-U`[,-2], "./gmAcorn-u.txt", sep=" ", row.names=FALSE)
write.table(groupedDay$Adversity[,-2], "./gmAdversity.txt", sep=" ", row.names=FALSE)
write.table(groupedDay$Affluent[,-2], "./gmAffluent.txt", sep=" ", row.names=FALSE)
write.table(groupedDay$Comfortable[,-2], "./gmComfortable.txt", sep=" ", row.names=FALSE)

#seasonality of 2013 combined groups
pn2013 <- subset(pn, grepl("2013", pn$DateTime))
sp2013 <- split(pn2013, pn2013$Acorn_grouped)
gm2013 <- lapply(sp2013, function(x) GroupedMeans(x))
par(mfrow=c(2,2))
plotSeasonality(gm2013$`ACORN-U`$DateTime, gm2013$`ACORN-U`$Mean, "Acorn-U")
plotSeasonality(gm2013$Adversity$DateTime, gm2013$Adversity$Mean, "Adversity")
plotSeasonality(gm2013$Affluent$DateTime, gm2013$Affluent$Mean, "Affluent")
plotSeasonality(gm2013$Comfortable$DateTime, gm2013$Comfortable$Mean, "Comfortable")

#seasonality of 2013 all groups
par(mfrow=c(1,1))
pngm2013 <- GroupedMeans(pn2013);
plotSeasonality(pngm2013$DateTime, pngm2013$Mean, "AllGroups");

#seasonality of all years all groupss
pngm <- GroupedMeans(pn)
s <- plotSeasonality(pngm$DateTime, pngm$Mean, "AllGroups");
colnames(s) <- c("Date", "Mean KWH per half hour per day")
View(s)
write.table(s, "./gmSeasonality.txt", sep=" ", row.names=FALSE)

plotSeasonality <- function(td, m, name) {
  days <- sapply(td, substring, 0, 10)
  t <- cbind(days, m)
  agg <- aggregate(as.numeric(t[,2]), list(t[,1]), mean)
  agg[,1] <- as.Date(agg[,1],format="%Y-%m-%d")
  x <- agg[,1]
  y <- agg[,2]
  plot(x,y, xlab="Date", ylab="mean KWH per half per day", ylim=(c(0,0.5)), main=name)
  lo <- lowess(x,y,f=3/10)
  lines(lo, col='red', lwd=2)
  return(agg)
}
