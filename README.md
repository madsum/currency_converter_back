# Currency converter spring boot 2 service

All the required task is done except Server-Timing. I couldn't find any Java api for this. It can be easily done by Vaadin as mentioned https://vaadin.com/directory/component/server-timing. I have good expertise with Vaadin. I didn't develop this project's frontend by Vaadin. Because most probably Nosto is using Reactjs for frontend. So I developed frontend by Reactjs.

CSRF and CSP security is implemented. Also, Instrumentation of the project is done by Prometheus and Grafana. You can quickly check it by:-

git clone git@github.com:madsum/currency_converter_back.git  
cd currency_converter_back  
docker-compose up

Prometheus will be running @ http://localhost:9090/ 
it will be configured to instrument Heroku service deployed @
https://currency-converter-back.herokuapp.com/

Grafana will be running http://localhost:3000/

Heroku backend Project: https://currency-converter-front.herokuapp.com/  
Heroku frontend Project: https://currency-converter-back.herokuapp.com/
p.s: Backend exposes only 1 get rest endpoint with 3 parameters i.e: https://currency-converter-back.herokuapp.com/excurrency?currency=eur&exCurrency=usd&amount=10   
 
Project backend source repository: git@github.com:madsum/currency_converter_back.git     
Project fronted source repository: git@github.com:madsum/currency_converter_front.git

Backend Docker image published: https://hub.docker.com/repository/docker/madsum/currency_converter_back  
Fronted Docker image published: https://hub.docker.com/repository/docker/madsum/currency_converter_front



