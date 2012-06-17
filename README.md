Ant tasks for Amazon Web Services
=============

It's a basic release, i started with it to fit my own needs. But im planning to make it more full featured. If there are any suggestions or you want to cooperate, let me know. I'm using maven to build the project.

Supported tasks:
 * aws-s3-create: create a new bucket
 * aws-s3-put: put files in a bucket
 * aws-s3-delete-bucket: delete a complete bucket (doesn't work yet)
 * aws-s3-delete-files: delete files in a bucket
 * aws-cloudfront-create: create a new cloufront distribution
 * aws-cloudfront-get: fetch info about a cloudfront distribution

Dependencies:
 * com/amazonaws/aws-java-sdk/1.2.4
 * commons-logging/commons-logging/1.1.1
 * org/apache/httpcomponents/httpcore/4.1
 * org/apache/httpcomponents/httpclient/4.1.1
 * commons-codec/commons-codec/1.3

Usage
The zip file (aws-ant-tasks-0.1-package.zip) on the left contains working examples. More examples will be added soon.

 * Download aws-ant-tasks-0.1-package.zip and unzip it
 * Enter your AWS accessKey and secretKey in the build.xml
 * After your are able to execute the examples
 ** ant exampleCreate
 ** ant exampleDelete

Setup in ant

	<?xml version="1.0" encoding="UTF-8"?>
	<project name="Example aws-ant-tasks" basedir=".">

		<!--
			Defining the classpath to the ant-tasks
			It includes the ant tasks and the dependencies
		-->
		<path id="aws.classpath">
			<fileset dir="./lib/" includes="*.jar"/>
		</path>

		<!--
			Task definitions:
				- aws-s3-create
				- aws-s3-put
				- aws-s3-delete-bucket
				- aws-s3-delete-files
				- aws-cloudfront-create
				- aws-cloudfront-get
		-->
		<taskdef resource="com/github/awsanttasks/ant/antlib.xml">
			<classpath>
				<path refid="aws.classpath"/>
			</classpath>
		</taskdef>


		<!--
			AWS Settings
		-->
		<property name="aws.accessKey" value="YourAccessKeyHere"/>
		<property name="aws.secretKey" value="YourSecretKeyHere"/>



		<!--
			Example 1: create a new bucket
		-->
		<target name="exampleCreate">
			<aws-s3-create
				accessKey="${aws.accessKey}"
				secretKey="${aws.secretKey}"
				bucket="exampleBucket"
				properties="true"/>

			<echo message="Id: ${ant.aws.s3.create.name}"/>
			<echo message="Ref: ${ant.aws.s3.create.creationDate}"/>
			<echo message="Status: ${ant.aws.s3.create.owner.name}"/>
			<echo message="Domain: ${ant.aws.s3.create.owner.id}"/>
		</target>


		<!--
			Example 2: delete a bucket
		-->
		<target name="exampleDelete">
			<aws-s3-delete-bucket
				accessKey="${aws.accessKey}"
				secretKey="${aws.secretKey}"
				bucket="exampleBucket"/>
		</target>


		<!--
			TODO: more examples :)
		-->

	</project>


