from locust import HttpUser, task, between
from faker import Faker
import random

fake = Faker()

class Papers(HttpUser):
    host = "http://localhost:8081"
    wait_time = between(0.5, 1.5)

    @task
    def crear_paper(self):
        payload = {
            "titulo": fake.sentence(nb_words=5),
            "anioPublicacion": random.randint(1990, 2025),
            "editorial": fake.company(),
            "isbn": f"{random.randint(100,999)}-{random.randint(1000,9999)}-{random.randint(1000,9999)}-{random.randint(0,9)}",
            "resumen": fake.paragraph(nb_sentences=3),
            "orcid": f"0000-{random.randint(1000,9999)}-{random.randint(1000,9999)}-{random.randint(1000,9999)}",
            "revista": fake.company(),
            "areaInvestigacion": fake.word(ext_word_list=["Inteligencia Artificial", "Biotecnología", "Ciencias Sociales", "Robótica", "Economía"]),
            "autorId": 1 
        }

        self.client.post("/papers", json=payload)
