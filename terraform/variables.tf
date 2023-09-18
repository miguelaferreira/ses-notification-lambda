variable "email_from" {
  description = "The address used in the 'FROM' field of the report emails."
  type        = string
}

variable "email_to" {
  description = "The address used in the 'TO' field of the report emails."
  type        = string
}

variable "architecture" {
  description = "The architecture of the lambda runtime: x86_64 or arm64."
  type        = string

  validation {
    condition     = contains(["x86_64", "arm64"], var.architecture)
    error_message = "Architecture needs to be x86_64 or arm64."
  }
}
