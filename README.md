# currency-exchange-rate

Build steps:
1. cd {project_directory}
2. gradle build
3. docker build .
4. docker run -p 8080:8080 {image}



Endpoints:
/rate?symbol={symbol} where symbol is 3-letter currency code (try '?symbol=123' to see available codes)
