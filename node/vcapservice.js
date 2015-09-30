var service_creds = {};

// Parse VCAP_SERVICES if running in Bluemix
if (process.env.VCAP_SERVICES)
{
	var env = JSON.parse(process.env.VCAP_SERVICES);

	//find the service
	if (env['postgresql']) {
		service_creds = env['postgresql'][0]['credentials'];
	}
	else {
		console.log('the service does not exist');
	}
} 

