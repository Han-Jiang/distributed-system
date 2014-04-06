package han.nds;

public class TextLine {
	
	public TextLine(String textLine) {
		int  index = textLine.indexOf(32);
		id = Integer.parseInt(textLine.substring(0,index));
		line = textLine.substring(index+1);
	}
	
	public int  id;
	
	public String line;
	
	@Override
	public String toString() {
		return id + " " + line;
	}

	
	@Override
	public int hashCode() {
		return line.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		TextLine tempLine = (TextLine)obj;
		return line.equals(tempLine.line);
	}
	

}
