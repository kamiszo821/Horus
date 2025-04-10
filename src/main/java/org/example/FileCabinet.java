package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public final class FileCabinet implements Cabinet {

	private final List<Folder> folders;

	public FileCabinet(List<Folder> folders) {
		this.folders = Objects.requireNonNull(folders, "Folders cannot be null");
	}

	@Override
	public Optional<Folder> findFolderByName(String name) {
		return findMatchingFolders(folders, f -> f.name().equals(name)).stream().findFirst();
	}

	@Override
	public List<Folder> findFoldersBySize(FolderSize size) {
		return findMatchingFolders(folders, f -> f.size() == size);
	}

	@Override
	public int count() {
		return findMatchingFolders(folders, f -> true).size();
	}

	private List<Folder> findMatchingFolders(List<Folder> folders, Predicate<Folder> filter) {
		List<Folder> result = new ArrayList<>();
		Deque<Folder> stack = new ArrayDeque<>(folders);
		while (!stack.isEmpty()) {
			Folder current = stack.pop();
			if (filter.test(current)) {
				result.add(current);
			}
			if (current instanceof MultiFolder multi) {
				for (Folder child : multi.getFolders()) {
					stack.push(child);
				}
			}
		}
		return result;
	}
}
