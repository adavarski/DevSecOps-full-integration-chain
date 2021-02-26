# Use AWS Terraform provider
provider "aws" {
  region = "us-east-2"
}


data "template_file" "myuserdata" {
  template = "${file("${path.cwd}/user-data.tpl")}"
}

# Create EC2 instance
resource "aws_instance" "jenkins-tf" {
  ami                    = var.ami
  key_name               = var.key_name
  vpc_security_group_ids = [aws_security_group.jenkins-tf.id]
  source_dest_check      = false
  instance_type          = var.instance_type
  user_data = "${data.template_file.myuserdata.template}"
  tags = {
    Name  = "Jenkins EC2 instance"
  }
}


# Create Security Group for EC2
resource "aws_security_group" "jenkins-tf" {
  name = "terraform-jenkins-sg"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }


  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}
