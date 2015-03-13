package arvato.yaas.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error")
public class ErrorResponseData
{
	private String number;
	private String source;
	private String description;
	private String helpFile;
	private String helpContext;

	public String getNumber()
	{
		return number;
	}

	@XmlElement(name = "Number")
	public void setNumber(final String number)
	{
		this.number = number;
	}

	public String getSource()
	{
		return source;
	}

	@XmlElement(name = "Source")
	public void setSource(final String source)
	{
		this.source = source;
	}

	public String getDescription()
	{
		return description;
	}

	@XmlElement(name = "Description")
	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getHelpFile()
	{
		return helpFile;
	}

	@XmlElement(name = "HelpFile")
	public void setHelpFile(final String helpFile)
	{
		this.helpFile = helpFile;
	}

	public String getHelpContext()
	{
		return helpContext;
	}

	@XmlElement(name = "HelpContext")
	public void setHelpContext(final String helpContext)
	{
		this.helpContext = helpContext;
	}

}
