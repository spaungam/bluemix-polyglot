import os
import json
import re


# Code sample for parsing the VCAP_SERVICES environment variable provided
# by Bluemix or CloudFoundry in Python. This is a sample of the VCAP_SERVICES
# environment variable of an application bound to a Bluemix Mongo Database
# service.
class BaseDbConnection:
    def __init__(self):
        self.init_db()

    def init_db(self):
        VCAP_SERVICES = os.getenv('VCAP_SERVICES')

        if VCAP_SERVICES:
            print("Setting up the mongo database in a Bluemix environment!")
            # Read the VCAP_SERVICES environment variable
            decoded_services = json.loads(VCAP_SERVICES)
            # Loop through the service instances to capture connection info
            for key, value in decoded_services.iteritems():
                # Looking for an instance of a Mongo Bluemix Service
                if key.startswith('mongo'):
                    mongo_creds = decoded_services[key][0]['credentials']
                    seq = (r'^mongodb\:\/\/(?P<username>[_\w]+):(?P<password>[-\w]+)@'
                           '(?P<host>[\.\w]+):(?P<port>\d+)/(?P<database>[_\w]+).*?$')
                    regex = re.compile(seq)
                    match = regex.search(mongo_creds['uri'])
                    # Deconstruct MongoURL connection information
                    parseURI = match.groupdict()
                    self.MONGO_HOST = parseURI['host']
                    self.MONGO_PORT = parseURI['port']
                    self.MONGO_USERNAME = parseURI['username']
                    self.MONGO_PASSWORD = parseURI['password']
                    self.MONGO_DBNAME = parseURI['database']
                    print (self.MONGO_DBNAME)
         else:
             print("VCAP_SERVICES environment variable has not been set")


