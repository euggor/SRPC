/**
 * 
 */
package helper;

import contract.VoidResponse;

/**
 * @author Yevgeny Go
 *
 */
public class VoidResponseImpl implements VoidResponse {
    private static final long serialVersionUID = 1L;
    private int id;

    /**
     * 
     * @param id
     */
    public VoidResponseImpl(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Object getServiceResult() {
        return null;
    }

}
