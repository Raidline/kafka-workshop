FROM node:20.11.1-alpine3.19 as build

COPY . .
COPY .env.prod .env

RUN npm install

RUN npm run build

FROM nginx:alpine

WORKDIR /app

COPY --from=build /dist .
COPY ./nginx.conf /etc/nginx/nginx.conf