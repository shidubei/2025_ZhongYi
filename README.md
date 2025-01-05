# README
## Introduction
**This is a solution for project, use Java SpringBoot framework complete the backend code, use ReactJS framework complete the frontend code. And combine them together in BackEnd File.**
## Dokcerfile Command
**To convert the project into a Docker image, I wrote the Dockerfile to help with the chanllenge,You can see it in [Backend/Dockerfile](./Backend/Dokcerfile),and I use JDK 17 from Docker image for this project, and expose 8080 port to listen this project when it is running, and use java command to run the generated jar file**
### Build and Run
**By the way, you can use this command below to run this project**
```docker
cd ./Backend
docker build -t <image_name> .
docker run -d -p 80:8080 <iamge_name>:latest
```
**The <image_name> can be custom, and then you can visit the project by input http://localhost:80 in the website**

