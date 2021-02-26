from django.contrib import admin
from django.urls import path

from visitors.service import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('visitors/', views.VisitorAPI.as_view()),
]
