package pl.library.api.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.borrow.Borrow;
import pl.library.adapters.mysql.model.borrow.BorrowStatus;
import pl.library.api.borrow.dto.AddBorrowResponse;
import pl.library.domain.borrow.BorrowServiceImpl;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowController {
    private final BorrowServiceImpl borrowService;

    // dto
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AddBorrowResponse addBorrow(@Valid @RequestBody Borrow borrow) throws UserNotFoundException {
        Borrow addedBorrow = borrowService.addition(borrow);
        return new AddBorrowResponse(addedBorrow.getId());
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Borrow> getAllBorrowsByStatus(@RequestParam BorrowStatus status) {
        return borrowService.getAllBorrowsByStatus(status);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Borrow changeBorrowStatus(@PathVariable Long id, @RequestParam BorrowStatus status) {
        return borrowService.changeBorrowStatus(id, status);
    }
}
