<a name="readme-top"></a>
<br />
<div>
  <h3 align="center">REST API with Spring Boot</h3>
</div>
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## About The Project

A simple REST API implemented using latest Spring Boot 3. This project aims to showcase the power of Spring Boot's Security,
Spring JPA, Hibernate ORM framework, Spring JDBC and the Spring IoC container as well as the major changes in Spring Boot 3. This project uses MariaDB (a fork of MySQL) 
for its database server. Basic CRUD operations can be done using exposed REST API endpoints after the project is compiled and run on the integrated server. This project also implements Java JWT for both authentication and authorization purposes.
The project can be deployed in a docker container or a kubernetes cluster. Docker deployment files and Kubernetes deployment files are included in the project.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

This project uses following libraries/frameworks.
* [![Spring][Spring-badge]][Spring-url]
* [![Spring Boot][Spring-boot-badge]][Spring-boot-url]
* [![Spring Security][spring-security-badge]][spring-security-url]
* [![JWT][jwt-badge]][jwt-url]
* [![Xampp][xampp-badge]][xampp-url]
* [![MariaDB][mariadb-badge]][mariadb-url]
* [![Hibernate][hibernate-badge]][hibernate-url]
* [![Maven][maven-badge]][maven-url]
* [![Docker][docker-badge]][docker-url]
* [![Kubernetes][kubernetes-badge]][kubernetes-url]

## Getting Started

To get started follow the below steps.

### Prerequisites

This project uses libraries mentioned above and Maven as the build tool. Make sure you have a working maven installation.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/saif-sohel/spring_boot_latest_v3.git
   ```
2. Install the dependencies and compile the project using Maven install.
   ```sh
   mvn install
   mvn compile
   ```
3. run the project by executing the main class.
   ```sh
   java /target/classes/PracticeBootApplication
   ```
   
### Docker
Docker deployment files are included in the project. You can deploy the project in a docker container using the docker-compose files.
1. Build the docker images using docker-compose.
   ```sh
   docker-compose -f docker-compose.yml build
   ```
2. Run the docker images using docker-compose.
   ```sh
    docker-compose -f docker-compose.yml up
    ```
### Kubernetes
Kubernetes deployment files are also included in the project. You can deploy the project in a kubernetes cluster using the deployment files.

1. Use MiniKube to create a local kubernetes cluster.
   ```sh
   minikube start
   ```
2. Deploy the project in the cluster using kubectl.
   ```sh
    kubectl apply -f deployment.yml
    ```
3. MiniKube dashboard can be used to view the deployed project.
   ```sh
    minikube dashboard
    ```
   


<p align="right">(<a href="#readme-top">back to top</a>)</p>

## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Saiful Islam Sohel - [@saif_lw_sohel](https://twitter.com/saif_lw_sohel) - saifulislamsohel30@gmail.com

Github: https://github.com/saif-sohel

<p align="right">(<a href="#readme-top">back to top</a>)</p>

[Spring-badge]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[spring-boot-badge]:https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot
[spring-boot-url]:https://spring.io/projects/spring-boot
[xampp-badge]:https://img.shields.io/badge/Xampp-F37623?style=for-the-badge&logo=xampp&logoColor=white
[xampp-url]:https://www.apachefriends.org/
[mariadb-badge]:https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white
[mariadb-url]:https://mariadb.org/
[jwt-badge]:https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white
[jwt-url]:https://jwt.io/
[spring-security-badge]:https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white
[spring-security-url]:https://spring.io/projects/spring-security
[hibernate-badge]:https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[hibernate-url]:https://hibernate.org/orm/
[maven-badge]:https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white
[maven-url]:https://maven.apache.org/
[docker-badge]:https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white
[docker-url]:https://www.docker.com/
[kubernetes-badge]:https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white
[kubernetes-url]:https://kubernetes.io/
