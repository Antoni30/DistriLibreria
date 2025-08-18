from locust import HttpUser, task, between
import random
import faker

fake = faker.Faker()

class AutoresUser(HttpUser):
    host = "http://localhost:8081"  # Asegúrate que este sea el puerto correcto de ms-publicaciones
    wait_time = between(0.5, 1.5)

    @task
    def crear_autor(self):
        payload = {
            "nombre": fake.first_name(),
            "apellido": fake.last_name(),
            "email": fake.email(),
            "nacionalidad": fake.country(),
            "institucion": fake.company(),
            "orcid": f"0000-{random.randint(1000,9999)}-{random.randint(1000,9999)}-{random.randint(1000,9999)}"
        }

        self.client.post("/autores", json=payload)
