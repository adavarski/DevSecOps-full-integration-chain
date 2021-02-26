variable "region" {
  description = "AWS region to host your infrastructure"
  default     = "us-east-2"
}

variable "key_name" {
  description = "Private key name to use with instance"
  default     = "demo"
}

variable "instance_type" {
  description = "AWS instance type"
  default     = "t2.micro"
}

variable "ami" {
  description = "AWS AMI latest"

  # Ubuntu 20.04 
  default = "ami-0a91cd140a1fc148a"
}

