class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

//		"/"(view:"/index")
//        "/"(controller: "transcription", action: "list",params:[sort:"requestDate",order:"desc"])
        "/"(controller: "audioFile", action: "index",params:[sort:"fileName",order:"asc"])
		"500"(view:'/error')
	}
}
