
# Cloudfoundry Hands-On

This repository contains an application that can be used as sample app to get started with deploying, monitoring, maintaining and integrating applications on Cloudfoundry Application Runtime. 

## Prerequisites

To run all the exercises in this hands-on tutorial, you will need these tools / prerequisites:

* Workstation with Internet access & browser and a terminal
* CF CLI for your OS
* JDK 1.8 for your OS
* Any Java or text editor of your choice

## How to use it

Clone this repository with `git clone https://github.com/tscn/hands-on-cf`. 

Target your Cloudfoundry environment with `cf api <url> --skip-ssl-validation`. (Insecure mode is needed if CA certificates are not installed on workstation.)

Login to your organization on Cloudfoundry `cf login -o <org>`.

Change working directory to the cloned repository and run `cf push`. This command will run a few seconds and finally print an address. Copy this address and open it with a browser. If you can see the frontend like in the screenshot below, you successfully finished the first step of the hands-on tutorial. Read the instructions of the application to proceed with the next steps.

![Screenshot](https://github.com/tscn/raw/master/docs/screenshot.png)