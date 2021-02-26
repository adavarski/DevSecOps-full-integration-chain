from django.db import models


class Visitor(models.Model):

    service_ip = models.CharField(max_length=16)
    client_ip = models.CharField(max_length=16)
    timestamp = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return 'Client IP [%s] Timestamp [%s]' % (
            self.client_ip, self.timestamp)
