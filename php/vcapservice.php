<?php

  class VcapService {
      public function VcapService(){
          $vcapservices = json_decode(getenv('VCAP_SERVICES'),true);
          $credential = $vcapservices["alchemy-api"][0]["credentials"];
				
          // credential keys will vary depending on the service 
          $key = $credential['apikey'];
      }

  }

?>
