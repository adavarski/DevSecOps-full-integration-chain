from rest_framework.serializers import ModelSerializer

from visitors.service.models import Visitor


class VisitorSerializer(ModelSerializer):

    class Meta:
        model = Visitor
        fields = ('id', 'client_ip', 'service_ip', 'timestamp')
