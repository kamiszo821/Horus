import java.util.List;
import java.util.Optional;

import org.example.BasicFolder;
import org.example.BasicMultiFolder;
import org.example.FileCabinet;
import org.example.Folder;
import org.example.FolderSize;
import org.example.MultiFolder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileCabinetTest {

	private static final String FOLDER_NAME = "FolderName";
	private static final String FOLDER_NAME_NOT_EXIST = "NotExist";
	private static final String FOLDER_NAME_1 = "Name1";
	private static final String FOLDER_NAME_2 = "Name2";
	private static final String FOLDER_NAME_NESTED = "Nested";

	@Test
	public void testFindFolderByName_found() {
		Folder folder = new BasicFolder(FOLDER_NAME, FolderSize.SMALL);
		FileCabinet cabinet = new FileCabinet(List.of(folder));

		Optional<Folder> result = cabinet.findFolderByName(FOLDER_NAME);

		assertTrue(result.isPresent());
		assertEquals(FOLDER_NAME, result.get().name());
	}

	@Test
	public void testFindFolderByName_notFound() {
		Folder folder = new BasicFolder(FOLDER_NAME, FolderSize.SMALL);
		FileCabinet cabinet = new FileCabinet(List.of(folder));

		Optional<Folder> result = cabinet.findFolderByName(FOLDER_NAME_NOT_EXIST);

		assertFalse(result.isPresent());
	}

	@Test
	public void testFindFoldersBySize() {
		Folder f1 = new BasicFolder(FOLDER_NAME_1, FolderSize.SMALL);
		Folder f2 = new BasicFolder(FOLDER_NAME_2, FolderSize.SMALL);
		FileCabinet cabinet = new FileCabinet(List.of(f1, f2));

		List<Folder> result = cabinet.findFoldersBySize(FolderSize.SMALL);

		assertEquals(2, result.size());
	}

	@Test
	public void testCount_withNestedFolders() {
		Folder f1 = new BasicFolder(FOLDER_NAME_1, FolderSize.SMALL);
		Folder f2 = new BasicFolder(FOLDER_NAME_2, FolderSize.MEDIUM);
		MultiFolder sub = new BasicMultiFolder(FOLDER_NAME_NESTED, FolderSize.LARGE, List.of(f1, f2));
		FileCabinet cabinet = new FileCabinet(List.of(sub));

		int count = cabinet.count();

		assertEquals(3, count);
	}

	@Test
	public void testEmptyCabinet() {
		FileCabinet cabinet = new FileCabinet(List.of());

		assertFalse(cabinet.findFolderByName(FOLDER_NAME).isPresent());
		assertEquals(0, cabinet.findFoldersBySize(FolderSize.SMALL).size());
		assertEquals(0, cabinet.count());
	}

	@Test
	public void testNestedEmptyMultiFolder() {
		MultiFolder emptyMulti = new BasicMultiFolder(FOLDER_NAME, FolderSize.LARGE, List.of());
		FileCabinet cabinet = new FileCabinet(List.of(emptyMulti));

		assertEquals(1, cabinet.count());
		assertEquals(1, cabinet.findFoldersBySize(FolderSize.LARGE).size());
		assertTrue(cabinet.findFolderByName(FOLDER_NAME).isPresent());
	}

}
