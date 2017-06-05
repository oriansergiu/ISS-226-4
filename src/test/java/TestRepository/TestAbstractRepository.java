package TestRepository;

import model.Abstract;
import model.Paper;
import service.impl.DefaultAbstractService;


public class TestAbstractRepository {

    public void Test()
    {
        Paper paper = new Paper();
        paper.setTitle("Lucrare1");

        Abstract entity1 = new Abstract();
        entity1.setPaper(paper);
        entity1.setText("ok");

        DefaultAbstractService defaultAbstractService = new DefaultAbstractService();

        defaultAbstractService.addAbstract(entity1);
        entity1.setText("ok1");
        defaultAbstractService.updateAbstract(entity1);
        System.out.println("TEXT ENTITATE" + entity1.getText());
        defaultAbstractService.delete(entity1);

        System.out.println("A mers.");

    }
}
