# currency-exchange-rate

Сервис сравнивает курс валюты за сегодня и за вчерашний день. Если сегодня курс стал выше (к базовой валюте, что указана в properties), то возвращается gif изображение, взятое https://giphy.com/ по тегу rich, а если курс стал ниже, то по тегу broke.


Run application steps:
1. cd {project_directory}
2. gradle bootrun


Docker run steps:
1. cd {project_directory}
2. gradle build
3. docker build .
4. docker run -p 8080:8080 {image}



Endpoints:
/rate?symbol={symbol} where symbol is 3-letter currency code (try '?symbol=123' to see available codes)
