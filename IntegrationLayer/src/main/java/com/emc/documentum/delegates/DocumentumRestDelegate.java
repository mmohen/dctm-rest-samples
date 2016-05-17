package com.emc.documentum.delegates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import com.emc.documentum.dtos.DocumentCreation;
import com.emc.documentum.dtos.DocumentumCabinet;
import com.emc.documentum.dtos.DocumentumDocument;
import com.emc.documentum.dtos.DocumentumFolder;
import com.emc.documentum.dtos.DocumentumObject;
import com.emc.documentum.dtos.DocumentumProperty;
import com.emc.documentum.exceptions.CanNotDeleteFolderException;
import com.emc.documentum.exceptions.DocumentCheckoutException;
import com.emc.documentum.exceptions.DocumentCreationException;
import com.emc.documentum.exceptions.DocumentumException;
import com.emc.documentum.exceptions.FolderCreationException;
import com.emc.documentum.exceptions.ObjectNotFoundException;
import com.emc.documentum.exceptions.RepositoryNotAvailableException;
import com.emc.documentum.model.JsonEntry;
import com.emc.documentum.model.JsonObject;
import com.emc.documentum.transformation.CoreRestTransformation;
import com.emc.documentum.wrappers.DCRestAPIWrapper;

@Component("DocumentumRestDelegate")
public class DocumentumRestDelegate implements DocumentumDelegate {

	Logger log = Logger.getLogger(DocumentumRestDelegate.class.getCanonicalName());
	@Autowired
	DCRestAPIWrapper dcAPI;

	@Override
	public String getIdentifier() {
		return "rest";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#createFolder(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public DocumentumFolder createFolder(String cabinetName, String folderName) throws DocumentumException {
		log.entering(DocumentumRestDelegate.class.getSimpleName(), "CreateFolder");
		JsonObject cabinet;
		JsonObject folder;
		try {
			cabinet = dcAPI.getCabinet(cabinetName);
			folder = dcAPI.createFolder(cabinet, folderName);
			return CoreRestTransformation.convertJsonObject(folder, DocumentumFolder.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (ObjectNotFoundException | FolderCreationException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DocumentumException("Unable to instantiate class of type " + DocumentumDocument.class.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#createFolder(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public DocumentumFolder createFolderByParentId(String parentId, String folderName) throws DocumentumException {
		log.entering(DocumentumRestDelegate.class.getSimpleName(), "CreateFolder");
		JsonObject parent = dcAPI.getObjectById(parentId);
		JsonObject folder;
		try {
			folder = dcAPI.createFolder(parent, folderName);
			return CoreRestTransformation.convertJsonObject(folder, DocumentumFolder.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (FolderCreationException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DocumentumException("Unable to instantiate class of type " + DocumentumDocument.class.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#createDocument(com.emc.
	 * documentum.dtos.DocumentCreation)
	 */
	@Override
	public DocumentumDocument createDocument(DocumentCreation docCreation) throws DocumentumException {
		log.entering(DocumentumRestDelegate.class.getSimpleName(), "createDocument");
		JsonObject document;
		JsonObject folder;
		try {
			folder = dcAPI.getObjectById(docCreation.getParentId());
			document = dcAPI.createDocument(folder, docCreation.getProperties());
			return CoreRestTransformation.convertJsonObject(document, DocumentumDocument.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (DocumentCreationException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DocumentumException("Unable to instantiate class of type " + DocumentumDocument.class.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#getCabinetByName(java.
	 * lang.String)
	 */
	@Override
	public DocumentumFolder getCabinetByName(String cabinetName) throws DocumentumException {

		try {
			return CoreRestTransformation.convertJsonObject(dcAPI.getCabinet(cabinetName), DocumentumCabinet.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (ObjectNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DocumentumException("Unable to instantiate class of type " + DocumentumCabinet.class.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#getObjectById(java.lang.
	 * String)
	 */
	@Override
	public DocumentumObject getObjectById(String cabinetId)
			throws ObjectNotFoundException, RepositoryNotAvailableException {
		try {
			return CoreRestTransformation.convertJsonObject(dcAPI.getObjectById(cabinetId));
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			// TODO Object Not Found Exception
			throw new ObjectNotFoundException("object " + cabinetId + " not found.");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.emc.documentum.delegates.DocumentumDelegate#getAllCabinets()
	 */
	@Override
	public ArrayList<DocumentumFolder> getAllCabinets() throws RepositoryNotAvailableException {
		try {
			// TODO cabinets pagination should be set on front end ...
			return CoreRestTransformation.convertCoreRSEntryList(dcAPI.getAllCabinets(1, 20), DocumentumFolder.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#getChildren(java.lang.
	 * String)
	 */
	@Override
	public ArrayList<DocumentumObject> getChildren(String folderId) throws RepositoryNotAvailableException {
		return this.getChildren(folderId, 1, 20);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emc.documentum.delegates.DocumentumDelegate#getDocumentContentById(
	 * java.lang.String)
	 */
	@Override
	public byte[] getDocumentContentById(String documentId)
			throws ObjectNotFoundException, RepositoryNotAvailableException {
		try {
			return dcAPI.getDocumentContentById(documentId);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		}
	}

	@Override
	public ArrayList<DocumentumObject> getDocumentByName(String name) throws RepositoryNotAvailableException {
		try {
			return CoreRestTransformation.convertCoreRSEntryList(dcAPI.getDocumentByName(name));
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (ObjectNotFoundException e) {
			return new ArrayList<DocumentumObject>();
		}

	}

	@Override
	public DocumentumDocument checkoutDocument(String documentId) throws DocumentumException {
		try {
			return CoreRestTransformation.convertJsonObject(dcAPI.checkOutDocument(documentId),
					DocumentumDocument.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DocumentumException("Unable to instantiate class of type " + DocumentumDocument.class.getName());
		}
	}

	@Override
	public DocumentumDocument checkinDocument(String documentId, byte[] content) throws DocumentumException {
		try {
			return CoreRestTransformation.convertJsonObject(dcAPI.checkinDocument(documentId, content),
					DocumentumDocument.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new DocumentumException("Unable to instantiate class of type " + DocumentumDocument.class.getName());
		}

	}

	public ArrayList<DocumentumFolder> getPaginatedResult(String folderId, int startIndex, int pageSize)
			throws RepositoryNotAvailableException {
		try {
			return dcAPI.getPaginatedResult(folderId, startIndex, pageSize);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		}
	}

	@Override
	public ArrayList<DocumentumObject> getChildren(String folderId, int pageNumber, int pageSize)
			throws RepositoryNotAvailableException {
		try {
			return CoreRestTransformation.convertCoreRSEntryList(dcAPI.getChildren(folderId, pageNumber, pageSize));
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (Exception e) {
			// TODO Object Not Found Exception
			throw e;
		}
	}

	@Override
	public ArrayList<DocumentumFolder> getAllCabinets(int pageNumber, int pageSize)
			throws RepositoryNotAvailableException {
		try {
			return CoreRestTransformation.convertCoreRSEntryList(dcAPI.getAllCabinets(pageNumber, pageSize),
					DocumentumFolder.class);
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteObject(String objectId, boolean deleteChildrenOrNot) throws CanNotDeleteFolderException {
		dcAPI.deleteObject(objectId, deleteChildrenOrNot);
	}

	@Override
	public DocumentumObject cancelCheckout(String documentId)
			throws RepositoryNotAvailableException, DocumentCheckoutException {
		try {
			return CoreRestTransformation.convertJsonObject(dcAPI.cancelCheckout(documentId));
		} catch (ResourceAccessException e) {
			throw new RepositoryNotAvailableException("CoreRest");
		}
	}

	@Override
	public DocumentumFolder createFolder(String parentId, HashMap<String, Object> properties)
			throws FolderCreationException, RepositoryNotAvailableException, DocumentumException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DocumentumDocument createDocument(String parentId, DocumentumDocument document)
			throws DocumentCreationException {
		JsonObject parent = dcAPI.getObjectById(parentId);
		HashMap<String, Object> properties = document.getPropertiesAsMap();
		if (!properties.containsKey("r_object_type")) {
			properties.put("r_object_type", "dm_document");
		}

		if (!properties.containsKey("object_name")) {
			properties.put("object_name", document.getName());
		}

		return (DocumentumDocument) CoreRestTransformation.convertJsonObject(dcAPI.createDocument(parent, properties));
	}

	@Override
	public ArrayList<DocumentumProperty> getObjectProperties(String objectId) throws RepositoryNotAvailableException {
		return CoreRestTransformation.convertJsonObject(dcAPI.getObjectById(objectId)).getProperties();
	}

	@Override
	public ArrayList<DocumentumObject> getDocumentRelationsByRelationName(String documentId, String relationName,
			int pageNumber) throws DocumentumException {
		return CoreRestTransformation
				.convertCoreRSEntryList(dcAPI.getDocumentDMNotesByRelationName(documentId, relationName));
	}

	@Override
	public ArrayList<DocumentumObject> getDocumentComments(String documentId, String relationName)
			throws DocumentumException {
		List<JsonEntry> jsonResponse = dcAPI.getDocumentDMNotesByRelationName(documentId, relationName);
		byte[] content = null;
		String s = null;
		if (jsonResponse == null) {
			jsonResponse = new ArrayList<JsonEntry>();
		}

		for (int i = 0; i < jsonResponse.size(); i++) {
			content = dcAPI
					.getDocumentContentById((String) jsonResponse.get(i).getContent().getProperties().get("child_id"));
			s = new String(Base64.decodeBase64(content));
			jsonResponse.get(i).getContent().getProperties().put("content", s);
			jsonResponse.get(i).getContent().getProperties().put("date", jsonResponse.get(i).getUpdated());
		}
		return CoreRestTransformation.convertCoreRSEntryList(jsonResponse);
	}

	@Override
	public DocumentumObject createDocumentAnnotation(String documentId, byte[] content,
			HashMap<String, Object> properties) throws DocumentumException {
		String annotationNameProperty = (String) properties.get("annotation_name");
		String annotationName = (annotationNameProperty == null)
				? documentId + "_Annot_" + ((int) (Math.random() * 10000)) : annotationNameProperty;

		String folderIdproperty = (String) properties.get("folder_id");
		String folderId = folderIdproperty == null
				? (String) ((List) (getObjectById(documentId).getPropertiesAsMap().get("i_folder_id"))).get(0)
				: folderIdproperty;

		String formatProperty = (String) properties.get("format");
		String format = formatProperty == null ? "crtext" : formatProperty;

		DocumentumObject note = CoreRestTransformation
				.convertJsonObject(dcAPI.createAnnotationWithContent(folderId, annotationName, content, format));
		dcAPI.createRelationShip("dm_relation", documentId, note.getId(), "DM_ANNOTATE", true);
		return note;
	}

	@Override
	public ArrayList<DocumentumObject> getRenditionsByDocumentId(String doumentId) {
		List<JsonEntry> list = dcAPI.getRenditionsByDocumentId(doumentId);
		return CoreRestTransformation.convertCoreRSEntryList(list);
	}

	@Override
	public DocumentumObject addCommentToDocument(String documentId, String comment) {
		try {
			String commentName = "comment" + documentId + "_Comm_" + ((int) (Math.random() * 10000));
			String folderId = (String) ((List) (getObjectById(documentId).getPropertiesAsMap().get("i_folder_id")))
					.get(0);
			String format = "crtext";
			byte[] commentBytes = comment.getBytes();

			JsonObject json = dcAPI.createAnnotationWithContent(folderId, commentName, commentBytes, format);
			DocumentumObject commentObject = CoreRestTransformation.convertJsonObject(json);
			// TODO change the relation type
			dcAPI.createRelationShip("dm_relation", documentId, commentObject.getId(), "dm_wf_email_template", true);
			return commentObject;
		} catch (ObjectNotFoundException | RepositoryNotAvailableException e) {
			e.printStackTrace();
		} catch (DocumentumException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DocumentumObject renameObject(String documentId, String newName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentumObject copyObject(String objectId, String targetFolderId)
			throws DocumentumException, RepositoryNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentumObject moveObject(String objectId, String targetFolderId)
			throws DocumentumException, RepositoryNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentumObject updateProperties(String objectId, Map<String, Object> newProperties)
			throws ObjectNotFoundException, RepositoryNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}
}