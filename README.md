# Hogwarts Live

## Setup
### Setting up the Google Map API Key
1. To gain access to the API Key for local development, go to https://console.cloud.google.com. Note: Make sure you are logged into your CodeU Student account and that your project is set to project ID "sp19-codeu-8-6487".
2. Go to "APIs & Services" --> "Credentials".
3. There, you should see a dashboard with 2 keys: "Map_API_Dev_Key" & "Map_API_Prod_Key". The only key you need is **Map_API_Dev_Key**. 
4. Click on **Map_API_Dev_Key** and at the top of the page you will be able to see a field called "API key" that contains the string of characters (aka the key) you need. 
5. Copy that key to your clipboard (CTRL + c).
6. Permanently set the environment variable ****GOOGLE_MAP_API_KEY**** to the key from your clipboard. If you know how to set environment variables permanently on your Operating System, go ahead and do so. Otherwise, here is a tutorial for [MacOS/Linux](https://medium.com/@himanshuagarwal1395/setting-up-environment-variables-in-macos-sierra-f5978369b255) and for [Windows](http://www.forbeslindesay.co.uk/post/42833119552/permanently-set-environment-variables-on-windows).
