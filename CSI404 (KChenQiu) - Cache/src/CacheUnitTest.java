import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class CacheUnitTest {
	//Used ProcessorUnitTest.java for basic Cache testing
	
	private Lexer lexer;
	private Parser parser;
	private Assembler assembler;
	private Processor processor;
	
	@Test
	public void ArrayTest() {
		List<String> str = null;
		
		try {
			str = Files.readAllLines(Paths.get("C:/Users/kevin/eclipse-workspace/CSI404 (KChenQiu) - Cache/ArrayCacheTest"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.size(); i++) {
			sb.append(str.get(i));
		}
		
		lexer = new Lexer(sb.toString());
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		String[] instructions = new String[assembler.getInstructions().size()];
		
		for(int i = 0; i < assembler.getInstructions().size(); i++) {
			instructions[i] = assembler.getInstructions().get(i);
		}
		
		MainMemory.load(instructions);
		
		SetUpArray();
		
		processor = new Processor();
		
		System.out.print("Array ");
		processor.run();
	}
	
	@Test
	public void LinkedListTest() {
		
	}
	
	@Test
	public void ReverseArrayTest() {
		List<String> str = null;
		
		try {
			str = Files.readAllLines(Paths.get("C:/Users/kevin/eclipse-workspace/CSI404 (KChenQiu) - Cache/ReverseArrayCacheTest"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.size(); i++) {
			sb.append(str.get(i));
		}
		
		lexer = new Lexer(sb.toString());		
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		

		
		String[] instructions = new String[assembler.getInstructions().size()];
		
		for(int i = 0; i < assembler.getInstructions().size(); i++) {
			instructions[i] = assembler.getInstructions().get(i);
		}
		
		MainMemory.load(instructions);
		
		SetUpArray();
		
		processor = new Processor();

		System.out.print("Reverse Array ");
		processor.run();
	}
	
	
	//create an array in main memory from 200-219
	private void SetUpArray() {
		Word address = new Word();		
		Word value = new Word();
		
		
		address.set(-2147483448);
		value.set(-2147483647);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147478134);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483525);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483440);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483555);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483230);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483550);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483400);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147415531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2141483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147480031);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483502);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483201);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-214748353);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483611);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483100);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
	}
	
	
	
	private void SetUpLinkedList() {
		Word address = new Word();		
		Word value = new Word();
		
		
		address.set(-2147483448);
		value.set(-2147483647);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147478134);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483525);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483440);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483555);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483230);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483550);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483400);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147415531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2141483531);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147480031);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483502);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483201);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-214748353);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483611);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483100);
		
		MainMemory.write(address, value);
		
		address.Increment();
		value.set(-2147483531);
		
		MainMemory.write(address, value);
	}
}
