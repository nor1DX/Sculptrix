package com.cgvsu.objreader;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {

	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	public static Model read(String fileContent) {
		Model result = new Model();

		int lineInd = 0;
		Scanner scanner = new Scanner(fileContent);
		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) {
				continue;
			}

			final String token = wordsInLine.get(0);
			wordsInLine.remove(0);

			++lineInd;
			switch (token) {
				case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
				case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
				case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
				// Парсинг полигонов
				case OBJ_FACE_TOKEN -> {
					result.polygons.add(parseFace(wordsInLine, lineInd));
					// Проверка на соответствие текстурных вершин в полигонах
					if (result.polygons.size() > 1 &&
							(result.polygons.get(result.polygons.size() - 2).getTextureVertexIndices().size() == 0) !=
									(result.polygons.get(result.polygons.size() - 1).getTextureVertexIndices().size() == 0)) {
						throw new ObjReaderException("Polygon has no texture vertices.", lineInd);
					}
				}
				default -> {
				}
			}
		}

		// Проверка на наличие хотя бы одного полигона
		if (result.polygons.size() == 0) {
			throw new ObjReaderException("OBJ has not any polygons", lineInd);
		}

		// Проверка на наличие хотя бы одной вершины
		if (result.vertices.size() == 0) {
			throw new ObjReaderException("OBJ has not any vertices", lineInd);
		}

		// Проверка индексов вершин, нормалей и текстурных вершин в полигонах
		for (Polygon list : result.polygons) {
			int vertexIndicesSize = result.vertices.size();
			int normalIndicesSize = result.normals.size();
			int textureIndicesSize = result.textureVertices.size();

			for (int i = 0; i < 3; i++) {
				int vertexIndex = list.getVertexIndices().get(i);
				// Преобразование отрицательных индексов в положительные
				if (vertexIndex < 0) {
					list.getVertexIndices().set(i, result.vertices.size() + 1 + vertexIndex);
					vertexIndex = list.getVertexIndices().get(i);
				}
				// Проверка на выход индекса за пределы допустимых значений
				if (vertexIndex >= result.vertices.size() || vertexIndex < 0) {
					throw new ObjReaderException("The polygon is specified incorrectly: vertex index out of bounds.",
							findLineInObj(
									scanner,
									OBJ_VERTEX_TOKEN,
									vertexIndicesSize));
				}
			}

			// Проверка индексов нормалей
			if (list.getNormalIndices().size() != 0) {
				for (int i = 0; i < 3; i++) {
					int normalIndex = list.getNormalIndices().get(i);
					if (normalIndex < 0) {
						list.getNormalIndices().set(i, result.normals.size() + 1 + normalIndex);
						normalIndex = list.getNormalIndices().get(i);
					}
					if (normalIndex >= result.normals.size() || normalIndex < 0) {
						throw new ObjReaderException("The polygon is specified incorrectly: normal index out of bounds.",
								findLineInObj(
										scanner,
										OBJ_NORMAL_TOKEN,
										normalIndicesSize));
					}
				}
			}

			// Проверка индексов текстурных вершин
			if (list.getTextureVertexIndices().size() != 0) {
				for (int i = 0; i < 3; i++) {
					int textureIndex = list.getTextureVertexIndices().get(i);
					if (textureIndex < 0) {
						list.getTextureVertexIndices().set(i, result.textureVertices.size() + 1 + textureIndex);
						textureIndex = list.getTextureVertexIndices().get(i);
					}
					if (textureIndex >= result.textureVertices.size() || textureIndex < 0) {
						throw new ObjReaderException("The polygon is specified incorrectly: texture index out of bounds.",
								findLineInObj(
										scanner,
										OBJ_TEXTURE_TOKEN,
										textureIndicesSize));
					}
				}
			}
		}
		return result;
	}

	// парсинг вершин
	protected static Vector3 parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			if (wordsInLineWithoutToken.size() > 3) {
				throw new ObjReaderException("Too much vertex arguments.", lineInd);
			}

			return new Vector3(new float[]{
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2))});


		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few vertex arguments.", lineInd);
		}
	}

	//парсинг текстурных вершин
	protected static Vector2 parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			if (wordsInLineWithoutToken.size() > 2) {
				throw new ObjReaderException("Too much texture vertex arguments.", lineInd);
			}

			return new Vector2(new float[]{
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1))});

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
		}
	}

	// парсинг нормалей
	protected static Vector3 parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		try {
			if (wordsInLineWithoutToken.size() > 3) {
				throw new ObjReaderException("Too much normal arguments.", lineInd);
			}

			return new Vector3(new float[]{
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2))});

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few normal arguments.", lineInd);
		}
	}

	// парсинга полигонов
	protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		if (wordsInLineWithoutToken.size() < 3) {
			throw new ObjReaderException("Polygon has too few vertices.", lineInd);
		}

		ArrayList<Integer> onePolygonVertexIndices = new ArrayList();
		ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();

		for (String s : wordsInLineWithoutToken) {
			parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
		}

		// Проверка на дублирование индексов вершин в полигоне
		if (findEqualites(onePolygonVertexIndices)) {
			throw new ObjReaderException("The polygon can`t contain the same vertex indices", lineInd);
		}

		Polygon result = new Polygon();
		result.setVertexIndices(onePolygonVertexIndices);
		result.setTextureVertexIndices(onePolygonTextureVertexIndices);
		result.setNormalIndices(onePolygonNormalIndices);
		return result;
	}

	protected static void parseFaceWord(
			String wordInLine,
			ArrayList<Integer> onePolygonVertexIndices,
			ArrayList<Integer> onePolygonTextureVertexIndices,
			ArrayList<Integer> onePolygonNormalIndices,
			int lineInd) {
		try {
			String[] wordIndices = wordInLine.split("/");
			switch (wordIndices.length) {
				case 1 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
				}
				case 2 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
					onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
				}
				case 3 -> {
					onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
					onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
					if (!wordIndices[1].equals("")) {
						onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
					}
				}
				default -> {
					throw new ObjReaderException("Invalid element size.", lineInd);
				}
			}

		} catch (NumberFormatException e) {
			throw new ObjReaderException("Failed to parse int value.", lineInd);

		} catch (IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few arguments.", lineInd);
		}
	}

	// Метод для поиска строки в файле OBJ по токену и индексу
	public static int findLineInObj(Scanner scanner, String token, Integer indexInPolygon) {
		int lineInd = 0;
		int rows = 1;
		while (scanner.hasNextLine()) {
			lineInd++;
			final String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) {
				continue;
			}

			final String trueToken = wordsInLine.get(0);
			wordsInLine.remove(0);

			if (token.equals(trueToken)) {
				if (rows != indexInPolygon) {
					rows++;
				} else {
					return lineInd;
				}
			}
		}
		return -1;
	}

	// Метод для проверки на дублирование индексов вершин в полигоне
	public static boolean findEqualites(ArrayList<Integer> onePolygonVertexIndices) {
		for (int i = 0; i < onePolygonVertexIndices.size() - 1; i++) {
			for (int j = i + 1; j < onePolygonVertexIndices.size(); j++) {
				if (onePolygonVertexIndices.get(i) == onePolygonVertexIndices.get(j)) return true;
			}
		}
		return false;
	}
}
