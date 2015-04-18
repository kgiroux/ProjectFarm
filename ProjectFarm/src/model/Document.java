package model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import model.exception.InvalidDataException;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class Document implements Serializable {

	private static final long serialVersionUID = 3404763898326246494L;
	
	private int id;
	private Blob file;
	private String documentPath;
	private String type;
	private Date added;
	
	public Document(int id, Blob file, String documentPath, String type, Date added) throws InvalidDataException {
		this.id = id;
		setFile(file);
		setDocumentPath(documentPath);
		setType(type);
		setAdded(added);
	}
	
	public int getId() {
		return id;
	}

	public Blob getFile() {
		return file;
	}

	public void setFile(Blob file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) throws InvalidDataException {
		/*File file = new File(documentPath);
		
		if(!file.exists()) {
			throw new InvalidDataException("File " + documentPath + " does not exists");
		}
		
		if(!file.isFile()) {
			throw new InvalidDataException("Path " + documentPath + " does not point to a file");
		}*/
		
		this.documentPath = documentPath;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

}
