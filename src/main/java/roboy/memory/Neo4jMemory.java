package roboy.memory;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import roboy.memory.nodes.RetrieveQueryTemplate;
import roboy.memory.nodes.MemoryNodeModel;
import roboy.ros.RosMainNode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Implements the high-level-querying tasks to the Memory services using RosMainNode.
 */
public class Neo4jMemory implements Memory<MemoryNodeModel>
{
    private static Neo4jMemory memory;
    private static RosMainNode rosMainNode;
    private Gson gson = new Gson();

    private Neo4jMemory (RosMainNode node){
        this.rosMainNode = node;
    }

    public static Neo4jMemory getInstance(RosMainNode node)
    {
        if (memory==null) {
            memory = new Neo4jMemory(node);
        }
        return memory;

    }

    public static  Neo4jMemory getInstance()
    {
        try{
            return memory;
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
            System.out.println("Memory wasn't initalized correctly. Use public static Neo4jMemory getInstance(RosMainNode node) instead.");
            return null;
        }

    }

    /**
     * Updating information in the memory for an EXISTING node with known ID.
     *
     * @param node Node with a set ID, and other properties to be set or updated.
     * @return true for success, false for fail
     */
    @Override
    public boolean save(MemoryNodeModel node) throws InterruptedException, IOException
    {
        String response = rosMainNode.UpdateMemoryQuery(node.toJSON(gson));
        return(response.contains("OK"));
    }

    /**
     * This query retrieves a a single node by its ID.
     *
     * @param  id the ID of requested
     * @return Node representation of the result.
     */
    public MemoryNodeModel getById(int id) throws InterruptedException, IOException
    {
        String result = rosMainNode.GetMemoryQuery("{'id':"+id+"}");
        return gson.fromJson(result, MemoryNodeModel.class);
    }

    /**
     * This is a classical database query which finds all matching nodes.
     *
     * @param  query the ID of requested
     * @return Array of  IDs (all nodes which correspond to the pattern).
     */
    public ArrayList<Integer> getByQuery(MemoryNodeModel query) throws InterruptedException, IOException
    {
        String result = rosMainNode.GetMemoryQuery(query.toJSON(gson));
        Type type = new TypeToken<HashMap<String, List<Integer>>>() {}.getType();
        HashMap<String, ArrayList<Integer>> list = gson.fromJson(result, type);
        return list.get("id");
    }

    public int create(MemoryNodeModel query) throws InterruptedException, IOException
    {
        String result = rosMainNode.CreateMemoryQuery(query.toJSON(gson));
        Type type = new TypeToken<Map<String,Integer>>() {}.getType();
        Map<String,Integer> list = gson.fromJson(result, type);
        return list.get("id");
    }

    /**
     * IF ONLY THE ID IS SET, THE NODE IN MEMORY WILL BE DELETED ENTIRELY.
     * Otherwise, the properties present in the query will be deleted.
     *
     * @param query StrippedQuery avoids accidentally deleting other fields than intended.
     */
    public boolean remove(MemoryNodeModel query) throws InterruptedException, IOException
    {
        //Remove all fields which were not explicitly set, for safety.
        query.setStripQuery(true);
        String response = rosMainNode.DeleteMemoryQuery(query.toJSON(gson));
        return(response.contains("OK"));
    }

    /**
     * //TODO Deprecated due to interface incompatibility, use getById or getByMatch
     *
     * @param query a GetByIDQuery instance
     * @return Array with a single node
     */
    @Override
    @Deprecated
    public List<MemoryNodeModel> retrieve(MemoryNodeModel query) throws InterruptedException, IOException
    {
        //TODO just dummy response
        List<MemoryNodeModel> l = new ArrayList();
        l.add(gson.fromJson("{'id':96,'relations':{'LIVE_IN':[28,23],'STUDY_AT':[16]}}", MemoryNodeModel.class));
        if (1<3) return l;
        List<MemoryNodeModel> result = new ArrayList<>();
        if (query.getClass().equals(RetrieveQueryTemplate.class)) {
            result.add(gson.fromJson(rosMainNode.GetMemoryQuery(query.toString()), MemoryNodeModel.class));
        }
        return result;
    }

}
