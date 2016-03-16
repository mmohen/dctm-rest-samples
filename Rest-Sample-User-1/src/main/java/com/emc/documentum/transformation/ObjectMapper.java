package com.emc.documentum.transformation;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Property;

import com.emc.documentum.constants.DocumentumProperties;
import com.emc.documentum.dtos.DocumentumDocument;
import com.emc.documentum.dtos.DocumentumFolder;
import com.emc.documentum.dtos.DocumentumObject;
import com.emc.documentum.model.JsonObject;

public class ObjectMapper {

	private ObjectMapper() {

	}

	public static DocumentumFolder convertCMISFolder(Folder cmisFolder) {
		DocumentumFolder folder = new DocumentumFolder();
		folder.setId(cmisFolder.getId());
		folder.setPath(cmisFolder.getPath());
		folder.setName(cmisFolder.getDescription());
		HashMap<String, Object> properties = new HashMap<>();
		for (Property<?> property : cmisFolder.getProperties()) {
			properties.put(property.getDisplayName(), property.getValue());
		}
		folder.setProperties(properties);
		return folder;
	}

	public static DocumentumDocument convertCMISDocument(Document cmisDocument) {
		DocumentumDocument document = new DocumentumDocument();
		document.setId(cmisDocument.getId());
		document.setName(cmisDocument.getName());
		document.setPath(cmisDocument.getPaths().get(0));
		HashMap<String, Object> properties = new HashMap<>();
		for (Property<?> property : cmisDocument.getProperties()) {
			properties.put(property.getDisplayName(), property.getValue());
		}
		document.setProperties(properties);
		return document;
	}

	public static DocumentumFolder convertCoreRSFolder(JsonObject restFolder) {
		DocumentumFolder folder = new DocumentumFolder();
		folder.setId((String) restFolder.getPropertyByName(DocumentumProperties.OBJECT_ID));
		ArrayList<?> folderPath = (ArrayList<?>) restFolder.getPropertyByName(DocumentumProperties.FOLDER_PATH);
		if(folderPath != null & folderPath.size() > 0){
			folder.setPath(folderPath.get(0).toString());
		}
		folder.setName(restFolder.getName());
		folder.setProperties(restFolder.getProperties());
		folder.setDefinition(restFolder.getDefinition());
		folder.setType(restFolder.getType());
		return folder;
	}
	
	public static DocumentumObject convertCoreRSObject(JsonObject restObject) {
		DocumentumObject object = new DocumentumObject();
		object.setId((String) restObject.getPropertyByName(DocumentumProperties.OBJECT_ID));
		object.setName(restObject.getName());
		object.setProperties(restObject.getProperties());
		object.setDefinition(restObject.getDefinition());
		object.setType(restObject.getType());
		return object;
	}
	
	public static DocumentumDocument convertCoreRSDocument(JsonObject restDocument) {
		DocumentumDocument document = new DocumentumDocument();
		document.setId((String) restDocument.getPropertyByName(DocumentumProperties.OBJECT_ID));
		document.setName(restDocument.getName());
		document.setProperties(restDocument.getProperties());
		document.setDefinition(restDocument.getDefinition());
		document.setType(restDocument.getType());
		return document;
	}
}
