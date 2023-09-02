resource "null_resource" "build_container" {
  # Changes to any instance of the cluster requires re-provisioning
  triggers = {
    always = timestamp()
  }

  provisioner "local-exec" {
    working_dir = dirname(path.cwd)

    command = "DOCKER_IMAGE=${local.container_image} ./gradlew dockerPush"
  }

  depends_on = [null_resource.login_ecr]
}

resource "null_resource" "login_ecr" {
  # Changes to any instance of the cluster requires re-provisioning
  triggers = {
    always = timestamp()
  }

  provisioner "local-exec" {
    working_dir = dirname(path.cwd)

    command = "echo ${data.aws_ecr_authorization_token.token.password} | docker login --username ${data.aws_ecr_authorization_token.token.user_name} --password-stdin ${local.account_id}.dkr.ecr.${local.region}.amazonaws.com"
  }
}
