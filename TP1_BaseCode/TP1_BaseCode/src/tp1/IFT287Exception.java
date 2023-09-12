package tp1;

/**
 * L'exception IFT287Exception est levee lorsqu'une transaction est inadequate.
 */
public final class IFT287Exception extends Exception
{
    private static final long serialVersionUID = 1L;

    public IFT287Exception(String message)
    {
        super(message);
    }
}