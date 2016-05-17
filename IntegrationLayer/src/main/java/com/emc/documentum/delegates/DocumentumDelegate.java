package com.emc.documentum.delegates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.emc.documentum.dtos.DocumentCreation;
import com.emc.documentum.dtos.DocumentumDocument;
import com.emc.documentum.dtos.DocumentumFolder;
import com.emc.documentum.dtos.DocumentumObject;
import com.emc.documentum.dtos.DocumentumProperty;
import com.emc.documentum.exceptions.CanNotDeleteFolderException;
import com.emc.documentum.exceptions.DocumentCheckinException;
import com.emc.documentum.exceptions.DocumentCheckoutException;
import com.emc.documentum.exceptions.DocumentCreationException;
import com.emc.documentum.exceptions.DocumentumException;
import com.emc.documentum.exceptions.FolderCreationException;
import com.emc.documentum.exceptions.ObjectNotFoundException;
import com.emc.documentum.exceptions.RepositoryNotAvailableException;

public interface DocumentumDelegate {

	String getIdentifier();

	/**
	 * Create a folder inside a cabinet
	 *
	 * @param cabinetName
	 *            The name of the cabinet where the folder will be created
	 * @param folderName
	 *            The name of the folder to be created
	 * @return
	 * @throws FolderCreationException
	 * @throws CabinetNotFoundException
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentumException
	 */
	DocumentumFolder createFolder(String cabinetName, String folderName) throws FolderCreationException,
			ObjectNotFoundException, RepositoryNotAvailableException, DocumentumException;

	@Deprecated
	DocumentumDocument createDocument(DocumentCreation docCreation) throws DocumentumException;

	/**
	 * Return a Cabinet given its name
	 *
	 * @param cabinetName
	 * @return
	 * @throws CabinetNotFoundException
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentumException
	 */
	DocumentumFolder getCabinetByName(String cabinetName)
			throws ObjectNotFoundException, RepositoryNotAvailableException, DocumentumException;

	/**
	 * Return a Documentum Object given it unique Id
	 *
	 * @param cabinetId
	 * @return
	 * @throws CabinetNotFoundException
	 * @throws RepositoryNotAvailableException
	 */
	DocumentumObject getObjectById(String cabinetId) throws ObjectNotFoundException, RepositoryNotAvailableException;

	@Deprecated
	/**
	 * Use the paginated version getAllCabinets(pageNumber,pageSize)
	 *
	 * @return
	 * @throws RepositoryNotAvailableException
	 */
	ArrayList<DocumentumFolder> getAllCabinets() throws RepositoryNotAvailableException;

	@Deprecated
	/**
	 * @deprecated Use the paginated version
	 *             getChildren(folderId,pageNumber,pageSize)
	 *             {@link getChildren(String folderId, int pageNumber, int
	 *             pageSize)}
	 * @author abdela15
	 * @param folderId
	 * @return
	 * @throws RepositoryNotAvailableException
	 */
	ArrayList<DocumentumObject> getChildren(String folderId)
			throws RepositoryNotAvailableException, DocumentumException;

	/**
	 * Returns an Array of Documentum Objects containing the children of the
	 * Folder with the given FolderId
	 *
	 * @param folderId
	 *            Unique Identifier of the folder
	 * @param pageNumber
	 *            The page number to be returned
	 * @param pageSize
	 *            The size of the page to be returned
	 * @return
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentumException
	 */
	ArrayList<DocumentumObject> getChildren(String folderId, int pageNumber, int pageSize)
			throws RepositoryNotAvailableException, DocumentumException;

	/**
	 * Returns a Base64 encoded string containing the content of the document
	 * with unique id documentId
	 *
	 * @param documentId
	 * @return
	 * @throws DocumentNotFoundException
	 * @throws RepositoryNotAvailableException
	 */
	byte[] getDocumentContentById(String documentId) throws ObjectNotFoundException, RepositoryNotAvailableException;

	/**
	 * Return a list of documents with object_name matching the input name
	 *
	 * @param name
	 * @return ArrayList of matching DocumentumObjects
	 * @throws RepositoryNotAvailableException
	 */
	ArrayList<DocumentumObject> getDocumentByName(String name) throws RepositoryNotAvailableException;

	/**
	 * Checkout a document identified by a documentId
	 *
	 * @param documentId
	 * @return
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentCheckoutException
	 * @throws DocumentumException
	 */
	DocumentumDocument checkoutDocument(String documentId)
			throws RepositoryNotAvailableException, DocumentCheckoutException, DocumentumException;

	/**
	 * Check in a document identified by a documentId using a Byte Array Stream
	 *
	 * @param documentId
	 * @param content
	 * @return
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentCheckinException
	 * @throws DocumentumException
	 */
	DocumentumDocument checkinDocument(String documentId, byte[] content)
			throws RepositoryNotAvailableException, DocumentCheckinException, DocumentumException;

	/**
	 * Create a Folder under a parent identified by parentId The folderName
	 * represents the Title and name of the created folder
	 *
	 * @param parentId
	 * @param folderName
	 * @return
	 * @throws FolderCreationException
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentumException
	 */
	DocumentumFolder createFolderByParentId(String parentId, String folderName)
			throws FolderCreationException, RepositoryNotAvailableException, DocumentumException;

	/**
	 * retrieve all cabinets
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws RepositoryNotAvailableException
	 */
	ArrayList<DocumentumFolder> getAllCabinets(int pageNumber, int pageSize) throws RepositoryNotAvailableException;

	/**
	 * Delete object
	 *
	 * @param objectId
	 * @param deleteChildrenOrNot
	 * @throws CanNotDeleteFolderException
	 */
	void deleteObject(String objectId, boolean deleteChildrenOrNot) throws CanNotDeleteFolderException;

	/**
	 * Cancel a checkout
	 *
	 * @param documentId
	 * @return
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentCheckoutException
	 */
	DocumentumObject cancelCheckout(String documentId)
			throws RepositoryNotAvailableException, DocumentCheckoutException;

	/**
	 * Create a Folder under a parent identified by parentId The folderName a
	 * HashMap of properties represent the folder properties
	 *
	 * @param parentId
	 * @param properties
	 * @return
	 * @throws FolderCreationException
	 * @throws ObjectNotFoundException
	 * @throws RepositoryNotAvailableException
	 * @throws DocumentumException
	 */
	DocumentumFolder createFolder(String parentId, HashMap<String, Object> properties) throws FolderCreationException,
			ObjectNotFoundException, RepositoryNotAvailableException, DocumentumException;

	DocumentumDocument createDocument(String parentId, DocumentumDocument docCreation)
			throws DocumentCreationException, RepositoryNotAvailableException;

	/**
	 * retrieve properties of an object
	 *
	 * @param objectId
	 * @return
	 * @throws RepositoryNotAvailableException
	 */
	ArrayList<DocumentumProperty> getObjectProperties(String objectId) throws RepositoryNotAvailableException;

	DocumentumObject createDocumentAnnotation(String documentId, byte[] content, HashMap<String, Object> properties)
			throws DocumentumException;

	ArrayList<DocumentumObject> getRenditionsByDocumentId(String doumentId);

	DocumentumObject addCommentToDocument(String documentId, String comment);

	ArrayList<DocumentumObject> getDocumentRelationsByRelationName(String documentId, String relationName,
			int pageNumber) throws DocumentumException;

	public ArrayList<DocumentumObject> getDocumentComments(String documentId, String relationName)
			throws DocumentumException;

	DocumentumObject renameObject(String objectId, String newName)
			throws ObjectNotFoundException, RepositoryNotAvailableException;

	DocumentumObject copyObject(String objectId, String targetFolderId)
			throws DocumentumException, RepositoryNotAvailableException;

	DocumentumObject moveObject(String objectId, String targetFolderId)
			throws DocumentumException, RepositoryNotAvailableException;

	DocumentumObject updateProperties(String objectId, Map<String, Object> newProperties)
			throws ObjectNotFoundException, RepositoryNotAvailableException;
}