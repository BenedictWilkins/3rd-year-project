library("TTR")
library(forecast)

plotTimeSeries <- function(timeseries, days = 10, name) {
  plot.ts(timeseries[0:(days*48)], ylab="KWH usage", main=name)
}

getData <- function(filename) {
  return(scan(filename))
}

getTimeSeriesDataset <- function(filename) {
  return(ts(scan(filename), frequency = 48))
}

auts = getTimeSeriesDataset("AcornUData.txt")
affts = getTimeSeriesDataset("AffluentData.txt")
comts = getTimeSeriesDataset("ComfortableData.txt")
advts = getTimeSeriesDataset("AdversityData.txt")

par(mfrow=c(2,2))

plotTimeSeries(auts, name="AcornU")
plotTimeSeries(affts, name="AffluentD")
plotTimeSeries(comts, name="Comfortable")
plotTimeSeries(advts, name="Adversity")


au <- getData("AcornUData.txt")
auts <- ts(au[0:480], frequency = 48)
autstest <- ts(au[480:600], frequency = 48);

auts.decom <- decompose(auts)
par(mfrow=c(1,1))
plot(auts.decom)

par(mfrow=c(1,2))
#Auto correlation function
acf(auts)
#Partial Auto correlation function
pacf(auts)

#Testing different methods
auts <- getTimeSeriesDataset("AcornUData.txt")

auts.ar.burg <- ar.burg(auts);
auts.ar.yw <- ar.yw(auts);
auts.ar.mle <- ar.mle(auts)

auts.ar.burg.fcast <- forecast(auts.ar.burg, h = 480)
auts.ar.yw.fcast <- forecast(auts.ar.yw, h = 480)
auts.ar.mle.fcast <- forecast(auts.ar.mle, h = 480)

par(mfrow=c(3,1), cex.main=2.5)
plot(auts.ar.burg.fcast, main = "Forecasts for AR(26) - Method: Burg",xlim=c(350,375))
plot(auts.ar.yw.fcast, main = "Forecasts for AR(5) - Method: Yule-Walker", xlim=c(350,375))
plot(auts.ar.mle.fcast, main = "Forecasts for AR(5) - Method: Max likelyhood estimation", xlim=c(350,375))

#Testing ARIMA model
auts.arima <- auto.arima(auts, order=c(1,0,0))
auts.arima.fcast <- forecast(auts.arima, h = 480)
plot(auts.arima.fcast, xlim=c(350,375)) #not a good prediction!
#for the future - try and make ARIMA work!

tsdiag(auts.arima)
Box.test(auts.arima$residuals, lag=1)


