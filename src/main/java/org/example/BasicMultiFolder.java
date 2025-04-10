package org.example;

import java.util.List;

public record BasicMultiFolder(String name, FolderSize size, List<Folder> folders) implements MultiFolder {

	@Override
	public List<Folder> getFolders() {
		return folders;
	}

}
