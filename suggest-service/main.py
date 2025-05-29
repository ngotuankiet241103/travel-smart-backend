from fastapi import FastAPI
from controller.recommendController import router

app = FastAPI()
app.include_router(router)