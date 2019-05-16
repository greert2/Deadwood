public class Scene {

    private Role[] roles;
    private int budget;
    private boolean visited;
    private String sceneName;
    private String description;


    //Scenes can either have two or three roles, so two constructors
    public Scene(String sceneName, String description, int budget, String role1, String role2, String role3) {
        roles = new Role[3];
        String arr[];
        this.sceneName = sceneName;
        this.description = description;
        this.budget = budget;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        roles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        roles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role3.split("_", 3);
        roles[2] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Scene(String sceneName, String description, int budget, String role1, String role2) {
        roles = new Role[2];
        String arr[];
        this.sceneName = sceneName;
        this.description = description;
        this.budget = budget;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        roles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        roles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Scene(String sceneName, String description, int budget, String role1) {
        roles = new Role[1];
        String arr[];
        this.sceneName = sceneName;
        this.description = description;
        this.budget = budget;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        roles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Role[] getRoles(){
        return roles;
    }

    public void visit() {
        visited = true;
        return;
    }

    public int getBudget() {
        return budget;
    }

    public void wrap() {

        return;
    }

    public String getDescription() {
        return description;
    }

    public String getSceneName() {
        return sceneName;
    }

}
