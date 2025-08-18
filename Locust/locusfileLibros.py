from locust import HttpUser, task, between
from faker import Faker
import random

fake = Faker()

class Libros(HttpUser):
    host = "http://localhost:8081" 
    wait_time = between(0.5, 1.5)

    @task
    def crear_libro(self):
        payload = {
            "titulo": fake.sentence(nb_words=4),
            "anioPublicacion": random.randint(1900, 2025),
            "editorial": fake.company(),
            "isbn": f"{random.randint(100,999)}-{random.randint(1000,9999)}-{random.randint(1000,9999)}-{random.randint(0,9)}",
            "genero": fake.word(ext_word_list=["Ficción", "Historia", "Ciencia", "Romance", "Fantasía"]),
            "numeroPaginas": random.randint(100, 800),
            "autorId": 1,
            "resumen": fake.paragraph(nb_sentences=3)
        }

        self.client.post("/libros", json=payload)
