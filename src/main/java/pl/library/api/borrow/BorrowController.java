package pl.library.api.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.domain.borrow.BorrowServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowController {
    private final BorrowServiceImpl borrowService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Borrow addBorrow(@Valid @RequestBody Borrow borrow) {
        return borrowService.addition(borrow);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Borrow> getAllBorrowsByStatus(@RequestParam BorrowStatus status) {
        return borrowService.getAllBorrowsByStatus(status);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Borrow changeBorrowStatus(@RequestParam Long id, @RequestParam BorrowStatus status) {
        return borrowService.changeBorrowStatus(id, status);
    }
}
