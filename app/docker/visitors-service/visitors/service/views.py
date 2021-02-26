import socket

from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.views import APIView

from visitors.service.models import Visitor
from visitors.service.serializers import VisitorSerializer


class VisitorAPI(APIView):

    def get(self, request):
        qs = Visitor.objects.order_by('-timestamp')[:10]
        s = VisitorSerializer(qs, many=True)
        return Response(s.data)

    def post(self, request):
        service_ip = socket.gethostbyname(socket.gethostname())
        client_ip = self.get_client_ip(request)

        v = Visitor(service_ip=service_ip,
                    client_ip=client_ip)
        v.save()

        s = VisitorSerializer(v)
        return Response(s.data)

    @staticmethod
    def get_client_ip(request):
        x_forwarded_for = request.META.get('HTTP_X_FORWARDED_FOR')
        if x_forwarded_for:
            ip = x_forwarded_for.split(',')[0]
        else:
            ip = request.META.get('REMOTE_ADDR')
        return ip
