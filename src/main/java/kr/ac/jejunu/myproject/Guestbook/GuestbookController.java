package kr.ac.jejunu.myproject.Guestbook;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guestbook")
@RequiredArgsConstructor
public class GuestbookController{
    private final GuestbookDao guestbookDao;
    @PostMapping("/add")
    public Guestbook add(@RequestBody Guestbook guestbook){
        return guestbookDao.save(guestbook);
    }

    @GetMapping("/{id}")
    public Guestbook get(@PathVariable Long id){
        return guestbookDao.findById(id).get();
    }

    @GetMapping("/all")
    public List<Guestbook> getAll(){
        return guestbookDao.findAll();
    }

    @PostMapping("/modify/{id}")
    public Guestbook modify(@PathVariable Long id, @RequestBody Guestbook guestbook){
        Guestbook guestbook1 = guestbookDao.findById(id).get();
        guestbook1.setContent(guestbook.getContent());
        return guestbookDao.save(guestbook1);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        guestbookDao.deleteById(id);
    }

}
